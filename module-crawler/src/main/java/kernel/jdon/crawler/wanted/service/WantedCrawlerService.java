package kernel.jdon.crawler.wanted.service;

import static kernel.jdon.util.StringUtil.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import kernel.jdon.config.UrlConfig;
import kernel.jdon.crawler.common.repository.SkillRepository;
import kernel.jdon.crawler.common.repository.WantedJdSkillRepository;
import kernel.jdon.crawler.wanted.converter.EntityConverter;
import kernel.jdon.crawler.wanted.dto.object.CreateSkillDto;
import kernel.jdon.crawler.wanted.dto.response.WantedJobDetailResponse;
import kernel.jdon.crawler.wanted.dto.response.WantedJobListResponse;
import kernel.jdon.crawler.wanted.repository.JobCategoryRepository;
import kernel.jdon.crawler.wanted.repository.WantedJdRepository;
import kernel.jdon.crawler.wanted.search.JobSearchExperience;
import kernel.jdon.crawler.wanted.search.JobSearchJobCategory;
import kernel.jdon.crawler.wanted.search.JobSearchJobPosition;
import kernel.jdon.crawler.wanted.search.JobSearchLocation;
import kernel.jdon.crawler.wanted.search.JobSearchSort;
import kernel.jdon.error.code.crawler.WantedErrorCode;
import kernel.jdon.error.exception.crawler.CrawlerException;
import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.skill.domain.Skill;
import kernel.jdon.wanted.domain.WantedJd;
import kernel.jdon.wantedskill.domain.WantedJdSkill;
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
	private final JobCategoryRepository jobCategoryRepository;
	@Value("${max_fetch_jd_list.size}")
	private int MAX_FETCH_JD_LIST_SIZE;
	@Value("${max_fetch_jd_list.offset}")
	private int MAX_FETCH_JD_LIST_OFFSET;

	@Transactional
	public void fetchJd() {
		JobSearchJobPosition[] jobPositions = {
			JobSearchJobPosition.JOB_POSITION_FRONTEND,
			JobSearchJobPosition.JOB_POSITION_SERVER
		};
		for (JobSearchJobPosition jobPosition : jobPositions) {
			Set<Long> fetchJobIds = fetchJobIdList(jobPosition);

			JobCategory findJobCategory = findByJobPosition(jobPosition);

			fetchJobDetail(findJobCategory, fetchJobIds);
		}
	}

	private JobCategory findByJobPosition(JobSearchJobPosition jobPosition) {
		return jobCategoryRepository.findByWantedCode(jobPosition.getSearchValue())
			.orElseThrow(() -> new CrawlerException(WantedErrorCode.NOT_FOUND_JOB_CATEGORY));
	}

	private void fetchJobDetail(JobCategory jobCategory, Set<Long> fetchJobIds) {
		HashMap<WantedJd, List<Skill>> wantedJdSkillMap = new HashMap<>();
		for (Long detailId : fetchJobIds) {
			if (isJobDetailExist(jobCategory, detailId)) {
				continue;
			}
			WantedJobDetailResponse jobDetailResponse = getJobDetail(jobCategory, detailId);
			WantedJd savedWantedJd = createWantedJd(jobDetailResponse);
			List<Skill> savedSkillList = createSkillList(jobCategory, jobDetailResponse);
			wantedJdSkillMap.put(savedWantedJd, savedSkillList);
		}
		createWantedJdSkill(wantedJdSkillMap);
	}

	private boolean isJobDetailExist(JobCategory jobCategory, Long detailId) {
		return wantedJdRepository.existsByJobCategoryAndDetailId(jobCategory, detailId);
	}

	private void createWantedJdSkill(HashMap<WantedJd, List<Skill>> wantedJdSkillMap) {
		for (Map.Entry<WantedJd, List<Skill>> entry : wantedJdSkillMap.entrySet()) {
			WantedJd wantedJd = entry.getKey();
			List<Skill> skillList = entry.getValue();
			List<WantedJdSkill> wantedJdSkillList = skillList.stream()
				.map(skill -> EntityConverter.createWantedJdSkill(wantedJd, skill))
				.toList();
			wantedJdSkillRepository.saveAll(wantedJdSkillList);
		}
	}

	private List<Skill> createSkillList(JobCategory jobCategory, WantedJobDetailResponse jobDetailResponse) {
		return jobDetailResponse.getJob().getSkill().stream()
			.map(wantedJdDetailSkill -> skillRepository.save(
				EntityConverter.createSkill(new CreateSkillDto(jobCategory, wantedJdDetailSkill.getKeyword())))
			)
			.toList();
	}

	private WantedJobDetailResponse getJobDetail(JobCategory jobCategory, Long detailId) {
		WantedJobDetailResponse wantedJobDetailResponse = fetchJobDetail(detailId);
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

	private WantedJobDetailResponse fetchJobDetail(Long jobId) {
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
