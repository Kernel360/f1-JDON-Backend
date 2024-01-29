package kernel.jdon.crawler.wanted.service;

import static kernel.jdon.util.StringUtil.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import kernel.jdon.crawler.config.ScrapingWantedConfig;
import kernel.jdon.crawler.global.error.code.WantedErrorCode;
import kernel.jdon.crawler.global.error.exception.CrawlerException;
import kernel.jdon.crawler.wanted.converter.EntityConverter;
import kernel.jdon.crawler.wanted.dto.object.CreateSkillDto;
import kernel.jdon.crawler.wanted.dto.response.WantedJobDetailResponse;
import kernel.jdon.crawler.wanted.dto.response.WantedJobListResponse;
import kernel.jdon.crawler.wanted.repository.JobCategoryRepository;
import kernel.jdon.crawler.wanted.repository.SkillHistoryRepository;
import kernel.jdon.crawler.wanted.repository.SkillRepository;
import kernel.jdon.crawler.wanted.repository.WantedJdRepository;
import kernel.jdon.crawler.wanted.repository.WantedJdSkillRepository;
import kernel.jdon.crawler.wanted.search.JobSearchExperience;
import kernel.jdon.crawler.wanted.search.JobSearchJobCategory;
import kernel.jdon.crawler.wanted.search.JobSearchJobPosition;
import kernel.jdon.crawler.wanted.search.JobSearchLocation;
import kernel.jdon.crawler.wanted.search.JobSearchSort;
import kernel.jdon.crawler.wanted.service.infrastructure.JobDetailProcessingCounter;
import kernel.jdon.crawler.wanted.service.infrastructure.JobListProcessingCounter;
import kernel.jdon.crawler.wanted.skill.BackendSkillType;
import kernel.jdon.crawler.wanted.skill.FrontendSkillType;
import kernel.jdon.crawler.wanted.skill.SkillType;
import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.skill.domain.Skill;
import kernel.jdon.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WantedCrawlerService {
	private final RestTemplate restTemplate;
	private final ScrapingWantedConfig scrapingWantedConfig;
	private final WantedJdRepository wantedJdRepository;
	private final WantedJdSkillRepository wantedJdSkillRepository;
	private final SkillRepository skillRepository;
	private final SkillHistoryRepository skillHistoryRepository;
	private final JobCategoryRepository jobCategoryRepository;

	@Transactional
	public void fetchJd() throws InterruptedException {
		for (JobSearchJobPosition jobPosition : JobSearchJobPosition.getAllPositions()) {
			Set<Long> fetchJobIds = fetchJobIdList(jobPosition);

			JobCategory findJobCategory = findByJobPosition(jobPosition);

			processJobDetails(jobPosition, findJobCategory, fetchJobIds);
		}
	}

	private JobCategory findByJobPosition(final JobSearchJobPosition jobPosition) {
		return jobCategoryRepository.findByWantedCode(jobPosition.getSearchValue())
			.orElseThrow(() -> new CrawlerException(WantedErrorCode.NOT_FOUND_JOB_CATEGORY));
	}

	private void processJobDetails(final JobSearchJobPosition jobPosition, final JobCategory jobCategory,
		final Set<Long> fetchJobIds) throws InterruptedException {
		JobDetailProcessingCounter jobProcessingCounter = new JobDetailProcessingCounter(scrapingWantedConfig);

		for (Long jobDetailId : fetchJobIds) {
			if (jobProcessingCounter.isBreakRequired()) {
				break;
			}
			if (isJobDetailExist(jobCategory, jobDetailId)) {
				jobProcessingCounter.incrementFailCount();
				continue;
			}
			if (jobProcessingCounter.isSleepRequired()) {
				performSleep();
				jobProcessingCounter.resetSleepCounter();
			}

			jobProcessingCounter.resetFailCount(); // 연속으로 JD가 추출되지 않았다면 초기화

			createJobDetail(jobPosition, jobCategory, jobDetailId);

			jobProcessingCounter.incrementSleepCounter();
		}
	}

	private void createJobDetail(final JobSearchJobPosition jobPosition, final JobCategory jobCategory, final Long jobDetailId) {
		WantedJobDetailResponse jobDetailResponse = getJobDetail(jobCategory, jobDetailId);
		WantedJd savedWantedJd = createWantedJd(jobDetailResponse);

		List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList =
			jobDetailResponse.getJob().getSkill();

		createSkillHistory(jobCategory, savedWantedJd, wantedDetailSkillList);
		createWantedJdSkill(jobPosition, jobCategory, savedWantedJd, wantedDetailSkillList);
	}

	private boolean isJobDetailExist(final JobCategory jobCategory, final Long jobDetailId) {
		return wantedJdRepository.existsByJobCategoryAndDetailId(jobCategory, jobDetailId);
	}

	private void createSkillHistory(final JobCategory jobCategory, final WantedJd wantedJd,
		List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {
		for (WantedJobDetailResponse.WantedSkill wantedJdDetailSkill : wantedDetailSkillList) {
			skillHistoryRepository.save(
				EntityConverter.createSkillHistory(
					new CreateSkillDto(jobCategory, wantedJd, wantedJdDetailSkill.getKeyword())));
		}
	}

	private void createWantedJdSkill(final JobSearchJobPosition jobPosition, final JobCategory jobCategory, WantedJd wantedJd,
		List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {
		//TODO : 전략패턴으로 리팩토링 필요
		SkillType[] skillTypes = (jobPosition == JobSearchJobPosition.JOB_POSITION_SERVER)
			? BackendSkillType.values()
			: FrontendSkillType.values();

		for (WantedJobDetailResponse.WantedSkill wantedSkill : wantedDetailSkillList) {
			String skillKeyword = wantedSkill.getKeyword();
			boolean isSkillInJobPosition = Arrays.stream(skillTypes)
				.anyMatch(skillType -> skillType.getKeyword().equalsIgnoreCase(skillKeyword));
			Skill findSkill = null;

			if (isSkillInJobPosition) {
				SkillType matchedSkillType = Arrays.stream(skillTypes)
					.filter(skillType -> skillType.getKeyword().equalsIgnoreCase(skillKeyword))
					.findFirst()
					.get();

				findSkill = findByJobCategoryIdAndKeyword(jobCategory, matchedSkillType.getKeyword());
			} else {
				findSkill = findByJobCategoryIdAndKeyword(jobCategory, SkillType.getOrderKeyword());
			}
			wantedJdSkillRepository.save(EntityConverter.createWantedJdSkill(wantedJd, findSkill));
		}
	}

	private Skill findByJobCategoryIdAndKeyword(final JobCategory jobCategory, final String matchedSkillType) {
		return skillRepository.findByJobCategoryIdAndKeyword(jobCategory.getId(), matchedSkillType)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 기술스택이 없음 -> 데이터베이스와 동기화되지 않은 키워드"));
	}

	private WantedJobDetailResponse getJobDetail(final JobCategory jobCategory, final Long jobDetailId) {
		WantedJobDetailResponse wantedJobDetailResponse = fetchJobDetail(jobDetailId);
		addWantedJobDetailResponse(wantedJobDetailResponse, jobCategory, jobDetailId);

		return wantedJobDetailResponse;
	}

	private void addWantedJobDetailResponse(final WantedJobDetailResponse jobDetailResponse, final JobCategory jobCategory,
		final Long jobDetailId) {
		final String jobUrlDetail = scrapingWantedConfig.getUrl().getDetail();
		jobDetailResponse.addDetailInfo(joinToString(jobUrlDetail, jobDetailId), jobCategory);
	}

	private WantedJd createWantedJd(final WantedJobDetailResponse jobDetailResponse) {
		return wantedJdRepository.save(EntityConverter.createWantedJd(jobDetailResponse));
	}

	private WantedJobDetailResponse fetchJobDetail(final Long jobId) {
		final String jobApiDetailUrl = scrapingWantedConfig.getUrl().getApi().getDetail();
		final String jobDetailUrl = joinToString(jobApiDetailUrl, jobId);

		return restTemplate.getForObject(jobDetailUrl, WantedJobDetailResponse.class);
	}

	private Set<Long> fetchJobIdList(final JobSearchJobPosition jobPosition) {
		JobListProcessingCounter jobListCounter = new JobListProcessingCounter(scrapingWantedConfig);

		while (jobListCounter.isBelowSizeLimit()) {
			WantedJobListResponse jobListResponse = fetchJobList(jobPosition, jobListCounter.getOffset());

			List<Long> jobIdList = jobListResponse.getData().stream()
				.map(WantedJobListResponse.Data::getId)
				.toList();

			jobListCounter.addFetchedJobIds(jobIdList);

			if (jobListCounter.isBelowOffsetLimit(jobIdList.size())) {
				break;
			}

			jobListCounter.incrementOffset();
		}

		return jobListCounter.getFetchedJobIds();
	}

	private WantedJobListResponse fetchJobList(final JobSearchJobPosition jobPosition, final int offset) {
		final String jobListUrl = createJobListUrl(jobPosition, offset);

		return restTemplate.getForObject(jobListUrl, WantedJobListResponse.class);
	}

	private String createJobListUrl(final JobSearchJobPosition jobPosition, final int offset) {
		final int maxFetchJDListOffset = scrapingWantedConfig.getMaxFetchJdList().getOffset();
		final String jobApiListUrl = scrapingWantedConfig.getUrl().getApi().getList();

		return joinToString(
			jobApiListUrl,
			createQueryString(JobSearchJobCategory.SEARCH_KEY, JobSearchJobCategory.JOB_DEVELOPER.getSearchValue()),
			createQueryString(JobSearchJobPosition.SEARCH_KEY, jobPosition.getSearchValue()),
			createQueryString(JobSearchSort.SEARCH_KEY, JobSearchSort.SORT_LATEST.getSearchValue()),
			createQueryString(JobSearchLocation.SEARCH_KEY, JobSearchLocation.LOCATIONS_ALL.getSearchValue()),
			createQueryString(JobSearchExperience.SEARCH_KEY, JobSearchExperience.EXPERIENCE_ALL.getSearchValue()),
			createQueryString("limit", String.valueOf(maxFetchJDListOffset)),
			createQueryString("offset", String.valueOf(offset))
		);
	}

	private void performSleep() throws InterruptedException {
		final int sleepTimeMillis = scrapingWantedConfig.getSleep().getTimeMillis();
		Thread.sleep(sleepTimeMillis);
	}
}
