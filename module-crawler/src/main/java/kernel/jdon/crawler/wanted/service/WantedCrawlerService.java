package kernel.jdon.crawler.wanted.service;

import static kernel.jdon.util.StringUtil.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import kernel.jdon.crawler.config.UrlConfig;
import kernel.jdon.crawler.global.error.code.WantedErrorCode;
import kernel.jdon.crawler.global.error.exception.CrawlerException;
import kernel.jdon.crawler.wanted.converter.EntityConverter;
import kernel.jdon.crawler.wanted.dto.object.CreateSkillDto;
import kernel.jdon.crawler.wanted.dto.response.WantedJobDetailResponse;
import kernel.jdon.crawler.wanted.dto.response.WantedJobListResponse;
import kernel.jdon.crawler.wanted.repository.SkillHistoryRepository;
import kernel.jdon.crawler.wanted.repository.SkillRepository;
import kernel.jdon.crawler.wanted.repository.WantedJdRepository;
import kernel.jdon.crawler.wanted.repository.WantedJdSkillRepository;
import kernel.jdon.crawler.wanted.search.JobSearchExperience;
import kernel.jdon.crawler.wanted.search.JobSearchJobCategory;
import kernel.jdon.crawler.wanted.search.JobSearchJobPosition;
import kernel.jdon.crawler.wanted.search.JobSearchLocation;
import kernel.jdon.crawler.wanted.search.JobSearchSort;
import kernel.jdon.crawler.wanted.skill.BackendSkillType;
import kernel.jdon.crawler.wanted.skill.FrontendSkillType;
import kernel.jdon.crawler.wanted.skill.SkillType;
import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.jobcategory.repository.JobCategoryRepository;
import kernel.jdon.skill.domain.Skill;
import kernel.jdon.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WantedCrawlerService {
	private final RestTemplate restTemplate;
	private final UrlConfig urlConfig;
	private final WantedJdRepository wantedJdRepository;
	private final WantedJdSkillRepository wantedJdSkillRepository;
	private final SkillRepository skillRepository;
	private final SkillHistoryRepository skillHistoryRepository;
	private final JobCategoryRepository jobCategoryRepository;
	@Value("${max_fetch_jd_list.size}")
	private int MAX_FETCH_JD_LIST_SIZE;
	@Value("${max_fetch_jd_list.offset}")
	private int MAX_FETCH_JD_LIST_OFFSET;
	private static final int SLEEP_TIME_MILLIS = 1000;

	@Transactional
	public void fetchJd() throws InterruptedException {
		JobSearchJobPosition[] jobPositions = {
			JobSearchJobPosition.JOB_POSITION_FRONTEND,
			JobSearchJobPosition.JOB_POSITION_SERVER
		};
		for (JobSearchJobPosition jobPosition : jobPositions) {
			Set<Long> fetchJobIds = fetchJobIdList(jobPosition);

			JobCategory findJobCategory = findByJobPosition(jobPosition);

			createJobDetail(jobPosition, findJobCategory, fetchJobIds);

			Thread.sleep(SLEEP_TIME_MILLIS);
		}
	}

	private JobCategory findByJobPosition(JobSearchJobPosition jobPosition) {
		return jobCategoryRepository.findByWantedCode(jobPosition.getSearchValue())
			.orElseThrow(() -> new CrawlerException(WantedErrorCode.NOT_FOUND_JOB_CATEGORY));
	}

	private void createJobDetail(JobSearchJobPosition jobPosition, JobCategory jobCategory, Set<Long> fetchJobIds) {
		for (Long detailId : fetchJobIds) {
			if (isJobDetailExist(jobCategory, detailId)) {
				continue;
			}
			WantedJobDetailResponse jobDetailResponse = getJobDetail(jobCategory, detailId);
			WantedJd savedWantedJd = createWantedJd(jobDetailResponse);

			List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList = jobDetailResponse.getJob().getSkill();

			createSkillHistory(jobCategory, savedWantedJd, wantedDetailSkillList);
			createWantedJdSkill(jobPosition, jobCategory, savedWantedJd, wantedDetailSkillList);
		}
	}

	private boolean isJobDetailExist(JobCategory jobCategory, Long detailId) {
		return wantedJdRepository.existsByJobCategoryAndDetailId(jobCategory, detailId);
	}

	private void createSkillHistory(JobCategory jobCategory, WantedJd wantedJd, List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {
		for (WantedJobDetailResponse.WantedSkill wantedJdDetailSkill : wantedDetailSkillList) {
			skillHistoryRepository.save(
				EntityConverter.createSkillHistory(new CreateSkillDto(jobCategory, wantedJd, wantedJdDetailSkill.getKeyword())));
		}
	}

	private void createWantedJdSkill(JobSearchJobPosition jobPosition, JobCategory jobCategory, WantedJd wantedJd, List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {
		//TODO : 전략패턴으로 리팩토링 필요
		SkillType[] skillTypes = (jobPosition == JobSearchJobPosition.JOB_POSITION_SERVER)
			? BackendSkillType.values()
			: FrontendSkillType.values();

		for (WantedJobDetailResponse.WantedSkill wantedSkill: wantedDetailSkillList) {
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

	private Skill findByJobCategoryIdAndKeyword(JobCategory jobCategory, String matchedSkillType) {
		return skillRepository.findByJobCategoryIdAndKeyword(jobCategory.getId(), matchedSkillType)
			.orElseThrow(() -> new IllegalArgumentException("해당하는 기술스택이 없음 -> 데이터베이스와 동기화되지 않은 키워드"));
	}

	private WantedJobDetailResponse getJobDetail(JobCategory jobCategory, Long detailId) {
		WantedJobDetailResponse wantedJobDetailResponse = createfetchJobDetail(detailId);
		addWantedJobDetailResponse(wantedJobDetailResponse, jobCategory, detailId);
		return wantedJobDetailResponse;
	}

	private void addWantedJobDetailResponse(WantedJobDetailResponse jobDetailResponse, JobCategory jobCategory,
		Long detailId) {
		jobDetailResponse.setDetailUrl(joinToString(urlConfig.getWantedJobDetailUrl(), detailId));
		jobDetailResponse.setJobCategory(jobCategory);
	}

	private WantedJd createWantedJd(WantedJobDetailResponse jobDetailResponse) {
		return wantedJdRepository.save(EntityConverter.createWantedJd(jobDetailResponse));
	}

	private WantedJobDetailResponse createfetchJobDetail(Long jobId) {
		String jobDetailUrl = joinToString(urlConfig.getWantedApiJobDetailUrl(), jobId);
		return restTemplate.getForObject(jobDetailUrl, WantedJobDetailResponse.class);
	}

	private Set<Long> fetchJobIdList(JobSearchJobPosition jobPosition) {
		Set<Long> fetchJobIds = new HashSet<>();
		int offset = 0;

		while (fetchJobIds.size() < MAX_FETCH_JD_LIST_SIZE) {
			WantedJobListResponse jobListResponse = fetchJobList(jobPosition, offset);

			List<Long> jobIds = jobListResponse.getData().stream()
				.map(WantedJobListResponse.Data::getId)
				.toList();

			fetchJobIds.addAll(jobIds);

			offset += MAX_FETCH_JD_LIST_OFFSET;
		}

		return fetchJobIds;
	}

	private WantedJobListResponse fetchJobList(JobSearchJobPosition jobPosition, int offset) {
		String jobListUrl = createJobListUrl(jobPosition, offset);
		return restTemplate.getForObject(jobListUrl, WantedJobListResponse.class);
	}

	private String createJobListUrl(JobSearchJobPosition jobPosition, int offset) {
		return joinToString(
			urlConfig.getWantedApiJobListUrl(),
			createQueryString(JobSearchJobCategory.SEARCH_KEY, JobSearchJobCategory.JOB_DEVELOPER.getSearchValue()),
			createQueryString(JobSearchJobPosition.SEARCH_KEY, jobPosition.getSearchValue()),
			createQueryString(JobSearchSort.SEARCH_KEY, JobSearchSort.SORT_LATEST.getSearchValue()),
			createQueryString(JobSearchLocation.SEARCH_KEY, JobSearchLocation.LOCATIONS_ALL.getSearchValue()),
			createQueryString(JobSearchExperience.SEARCH_KEY, JobSearchExperience.EXPERIENCE_ALL.getSearchValue()),
			createQueryString("limit", String.valueOf(MAX_FETCH_JD_LIST_OFFSET)),
			createQueryString("offset", String.valueOf(offset))
		);
	}

}
