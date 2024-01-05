package kernel.jdon.crawler.wanted.service;

import static kernel.jdon.util.StringUtil.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import kernel.jdon.crawler.wanted.vo.SkillVo;
import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.skill.domain.Skill;
import kernel.jdon.wanted.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WantedCrawlerService {
	private static final int MAX_FETCH_JD_LIST_SIZE = 10; // 1000
	private static final int MAX_FETCH_JD_LIST_OFFSET = 5; // 100
	private final RestTemplate restTemplate;
	private final UrlConfig urlConfig;
	private final WantedJdRepository wantedJdRepository;
	private final WantedJdSkillRepository wantedJdSkillRepository;
	private final SkillRepository skillRepository;
	private final JobCategoryRepository jobCategoryRepository;

	@Transactional
	public void fetchJd() {
		JobSearchJobPosition[] jobPositions = {
			JobSearchJobPosition.JOB_POSITION_FRONTEND,
			JobSearchJobPosition.JOB_POSITION_SERVER
		};

		for (JobSearchJobPosition jobPosition : jobPositions) {
			Set<Long> fetchJobIds = fetchJobIdList(jobPosition);

			JobCategory findJobCategory = jobCategoryRepository.findByWantedCode(jobPosition.getSearchValue())
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 직군 또는 직무 입니다."));

			fetchJobDetail(findJobCategory, fetchJobIds);
		}
	}

	private void fetchJobDetail(JobCategory jobCategory, Set<Long> fetchJobIds) {
		HashMap<SkillVo, Long> wantedSkillAndCountMap = new HashMap<>();
		HashMap<WantedJd, List<WantedJobDetailResponse.WantedSkill>> wantedJdDetailSkillMap = new HashMap<>();
		for (Long detailId : fetchJobIds) {
			boolean isJobDetailExist = wantedJdRepository.existsByJobCategoryAndDetailId(jobCategory, detailId);
			if (isJobDetailExist) {
				continue;
			}
			/** 원티드 JD 상세 정보 등록 **/
			WantedJobDetailResponse jobDetailResponse = fetchJobDetail(detailId);
			jobDetailResponse.setDetailUrl(joinToString(urlConfig.getWantedJobDetailUrl(), detailId));
			jobDetailResponse.setJobCategory(jobCategory);
			WantedJd savedWantedJd = wantedJdRepository.save(EntityConverter.createWantedJd(jobDetailResponse));

			wantedJdDetailSkillMap.put(savedWantedJd, jobDetailResponse.getJob().getSkill());

			/** 원티드 JD 상세에서 추출된 기술스택 및 count를 임시 저장 **/
			for (WantedJobDetailResponse.WantedSkill wantedSkill : jobDetailResponse.getJob().getSkill()) {
				wantedSkillAndCountMap.put(
					new SkillVo(jobCategory.getId(), wantedSkill.getKeyword()),
					wantedSkillAndCountMap.getOrDefault(new SkillVo(jobCategory.getId(), wantedSkill.getKeyword()), 0L)
						+ 1L
				);
			}
		}

		/** 원티드 JD 상세에서 추출된 기술스택 저장 **/
		for (Map.Entry<SkillVo, Long> entry : wantedSkillAndCountMap.entrySet()) {
			String skillKeyword = entry.getKey().getKeyword();
			Long skillCount = entry.getValue();
			boolean isKeywordExist = skillRepository.existsByJobCategoryIdAndKeyword(jobCategory.getId(), skillKeyword);
			if (isKeywordExist) {
				Skill findSkill = findByJobCategoryIdAndKeyword(jobCategory.getId(), skillKeyword);
				findSkill.countPlus(skillCount);
			} else {
				skillRepository.save(
					EntityConverter.createSkill(new CreateSkillDto(jobCategory, skillKeyword, skillCount)));
			}
		}

		/** 원티드 JD 상세 - 기술 중간테이블 저장 **/
		for (Map.Entry<WantedJd, List<WantedJobDetailResponse.WantedSkill>> entry : wantedJdDetailSkillMap.entrySet()) {
			WantedJd wantedJd = entry.getKey();
			List<WantedJobDetailResponse.WantedSkill> targetJdSkillList = entry.getValue();
			for (WantedJobDetailResponse.WantedSkill skill : targetJdSkillList) {
				Skill findSkill = findByJobCategoryIdAndKeyword(jobCategory.getId(), skill.getKeyword());
				wantedJdSkillRepository.save(EntityConverter.createWantedJdSkill(wantedJd, findSkill));
			}
		}
	}

	private Skill findByJobCategoryIdAndKeyword(Long jobCategoryId, String skillKeyword) {
		return skillRepository.findByJobCategoryIdAndKeyword(jobCategoryId, skillKeyword)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 기술스택 입니다."));
	}

	private WantedJobDetailResponse fetchJobDetail(Long jobId) {
		String jobDetailUrl = joinToString(urlConfig.getWantedApiJobDetailUrl(), jobId);
		return restTemplate.getForObject(jobDetailUrl, WantedJobDetailResponse.class);
	}

	private Set<Long> fetchJobIdList(JobSearchJobPosition jobPosition) {
		int offset = 0;
		Set<Long> fetchJobIds = new HashSet<>();

		while (fetchJobIds.size() < MAX_FETCH_JD_LIST_SIZE) {
			WantedJobListResponse jobListResponse = fetchJobList(jobPosition, offset);

			jobListResponse.getData().stream()
				.forEach(detail -> fetchJobIds.add(detail.getId()));

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
