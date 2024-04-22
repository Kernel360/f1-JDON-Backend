package kernel.jdon.modulecrawler.domain.jd.core;

import static kernel.jdon.modulecommon.util.StringUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import kernel.jdon.modulecrawler.domain.jd.core.condition.JobSearchExperience;
import kernel.jdon.modulecrawler.domain.jd.core.condition.JobSearchJobCategory;
import kernel.jdon.modulecrawler.domain.jd.core.condition.JobSearchJobPosition;
import kernel.jdon.modulecrawler.domain.jd.core.condition.JobSearchLocation;
import kernel.jdon.modulecrawler.domain.jd.core.condition.JobSearchSort;
import kernel.jdon.modulecrawler.domain.jd.core.dto.PartJobDetailListInfo;
import kernel.jdon.modulecrawler.domain.jd.core.dto.WantedJobDetailResponse;
import kernel.jdon.modulecrawler.domain.jd.core.dto.WantedJobListResponse;
import kernel.jdon.modulecrawler.domain.jd.core.fetchmanager.JobDetailFetchManager;
import kernel.jdon.modulecrawler.domain.jd.core.fetchmanager.JobListFetchManager;
import kernel.jdon.modulecrawler.domain.jd.core.skillhistory.SkillHistoryStore;
import kernel.jdon.modulecrawler.domain.jd.core.wantedjdskill.WantedJdSkillStore;
import kernel.jdon.modulecrawler.domain.jobcategory.core.JobCategoryReader;
import kernel.jdon.modulecrawler.global.config.ScrapingWantedProperties;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JdServiceImpl implements JdService {
    private final RestTemplate restTemplate;
    private final ScrapingWantedProperties scrapingWantedProperties;
    private final JobCategoryReader jobCategoryReader;
    private final JdReader jdReader;
    private final JdStore jdStore;
    private final WantedJdSkillStore wantedJdSkillStore;
    private final SkillHistoryStore skillHistoryStore;

    @Override
    @Transactional
    public void scrapeWantedJd() {
        final JobSearchJobPosition[] allJobPositions = JobSearchJobPosition.getAllPositions();

        for (JobSearchJobPosition jobPosition : allJobPositions) {
            scrapeJobPositionWantedJd(jobPosition);
        }
    }

    private void scrapeJobPositionWantedJd(final JobSearchJobPosition jobPosition) {
        final JobListFetchManager jobListFetchManager = new JobListFetchManager(scrapingWantedProperties);
        boolean isContinueFetch = false;

        while (!isContinueFetch) {
            final PartJobDetailListInfo partJobDetailList = getPartJobDetailList(jobPosition,
                jobListFetchManager.getOffset());

            createJobDetail(partJobDetailList.getJobDetailList());

            if (partJobDetailList.isMaxDuplicate() || partJobDetailList.getJobDetailList().isEmpty()) {
                isContinueFetch = true;
            }

            jobListFetchManager.incrementOffset();
        }
    }

    public PartJobDetailListInfo getPartJobDetailList(final JobSearchJobPosition jobPosition,
        final int offset) {
        final WantedJobListResponse jobList = fetchJobList(jobPosition, offset);
        final Set<Long> jobIdSet = jobList.toLinkedHashSet();

        return getJobDetailList(jobPosition, jobIdSet);
    }

    private WantedJobListResponse fetchJobList(final JobSearchJobPosition jobPosition, final int offset) {
        final String jobListUrl = createJobListUrl(jobPosition, offset);

        return restTemplate.getForObject(jobListUrl, WantedJobListResponse.class);
    }

    private String createJobListUrl(final JobSearchJobPosition jobPosition, final int offset) {
        final int limit = scrapingWantedProperties.getJobListLimit();
        final String jobApiListUrl = scrapingWantedProperties.getApiListUrl();

        return joinToString(
            jobApiListUrl,
            createQueryString(JobSearchJobCategory.SEARCH_KEY, JobSearchJobCategory.JOB_DEVELOPER.getSearchValue()),
            createQueryString(JobSearchJobPosition.SEARCH_KEY, jobPosition.getSearchValue()),
            createQueryString(JobSearchSort.SEARCH_KEY, JobSearchSort.SORT_LATEST.getSearchValue()),
            createQueryString(JobSearchLocation.SEARCH_KEY, JobSearchLocation.LOCATIONS_ALL.getSearchValue()),
            createQueryString(JobSearchExperience.SEARCH_KEY, JobSearchExperience.EXPERIENCE_ALL.getSearchValue()),
            createQueryString("limit", String.valueOf(limit)),
            createQueryString("offset", String.valueOf(offset))
        );
    }

    private PartJobDetailListInfo getJobDetailList(final JobSearchJobPosition jobPosition,
        final Set<Long> jobIdSet) {
        final JobDetailFetchManager jobDetailFetchManager = new JobDetailFetchManager(scrapingWantedProperties);
        final List<WantedJobDetailResponse> jobDetailList = new ArrayList<>();
        int duplicateCount = 0;
        boolean isMaxDuplicate = false;

        for (Long jobDetailId : jobIdSet) {
            if (jobDetailFetchManager.isDuplicateRequired(duplicateCount)) {
                isMaxDuplicate = true;
                return new PartJobDetailListInfo(isMaxDuplicate, jobDetailList); // 중복된 채용공고 스크래핑 시 Reader 종료
            }

            final WantedJobDetailResponse jobDetail = getJobDetail(jobPosition, jobDetailId);

            if (isJobDetailExist(jobDetail.getJobCategory(), jobDetail.getDetailJobId())) {
                duplicateCount++;
            } else {
                duplicateCount = 0;
                jobDetailFetchManager.incrementSleepCounter();
                jobDetailList.add(jobDetail);
            }
        }

        return new PartJobDetailListInfo(isMaxDuplicate, jobDetailList);
    }

    private WantedJobDetailResponse getJobDetail(final JobSearchJobPosition jobPosition, final Long jobDetailId) {
        final WantedJobDetailResponse jobDetail = fetchJobDetail(jobDetailId);
        addJobDetailInfo(jobDetail, jobPosition);

        return jobDetail;
    }

    private WantedJobDetailResponse fetchJobDetail(final Long jobId) {
        final String jobApiDetailUrl = scrapingWantedProperties.getApiDetailUrl();
        final String jobDetailUrl = joinToString(jobApiDetailUrl, jobId);

        return restTemplate.getForObject(jobDetailUrl, WantedJobDetailResponse.class);
    }

    private void addJobDetailInfo(final WantedJobDetailResponse jobDetail, final JobSearchJobPosition jobPosition) {
        final JobCategory findJobCategory = findByJobPosition(jobPosition);

        jobDetail.addDetailInfo(scrapingWantedProperties.getDetailUrl(), findJobCategory, jobPosition);
    }

    private JobCategory findByJobPosition(final JobSearchJobPosition jobPosition) {
        return jobCategoryReader.findByWantedCode(jobPosition.getSearchValue());
    }

    private boolean isJobDetailExist(final JobCategory jobCategory, final Long jobDetailId) {
        return jdReader.existsByJobCategoryAndDetailId(jobCategory, jobDetailId);
    }

    private void createJobDetail(List<WantedJobDetailResponse> wantedJobDetailList) {
        for (WantedJobDetailResponse wantedJobDetail : wantedJobDetailList) {
            final JobCategory jdJobCategory = wantedJobDetail.getJobCategory();
            final List<WantedJobDetailResponse.WantedSkill> wantedJdSkillList = wantedJobDetail.getJobDetailSkillList();

            final WantedJd savedWantedJd = jdStore.save(wantedJobDetail.toWantedJdEntity());
            createWantedJdSkill(wantedJobDetail, savedWantedJd, wantedJdSkillList);
            createSkillHistory(jdJobCategory, savedWantedJd, wantedJdSkillList);
        }
    }

    /** 원본 기술스택 명 이력 저장 **/
    private void createSkillHistory(final JobCategory jobCategory, final WantedJd wantedJd,
        final List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {
        skillHistoryStore.saveSkillHistoryList(jobCategory.getId(), wantedJd.getId(), wantedDetailSkillList);
    }

    /** 기술스택 저장 **/
    private void createWantedJdSkill(
        final WantedJobDetailResponse wantedJobDetail,
        final WantedJd wantedJd,
        final List<WantedJobDetailResponse.WantedSkill> wantedDetailSkillList) {
        wantedJdSkillStore.saveWantedJdSkillList(wantedJobDetail, wantedJd, wantedDetailSkillList);
    }
}
