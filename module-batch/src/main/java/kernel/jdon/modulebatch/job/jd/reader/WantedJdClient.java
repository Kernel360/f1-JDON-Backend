package kernel.jdon.modulebatch.job.jd.reader;

import static kernel.jdon.modulecommon.util.StringUtil.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import kernel.jdon.modulebatch.domain.jobcategory.error.JobCategoryErrorCode;
import kernel.jdon.modulebatch.domain.jobcategory.repository.JobCategoryRepository;
import kernel.jdon.modulebatch.domain.wantedjd.repository.WantedJdRepository;
import kernel.jdon.modulebatch.global.config.ScrapingWantedProperties;
import kernel.jdon.modulebatch.job.jd.reader.condition.JobSearchExperience;
import kernel.jdon.modulebatch.job.jd.reader.condition.JobSearchJobCategory;
import kernel.jdon.modulebatch.job.jd.reader.condition.JobSearchJobPosition;
import kernel.jdon.modulebatch.job.jd.reader.condition.JobSearchLocation;
import kernel.jdon.modulebatch.job.jd.reader.condition.JobSearchSort;
import kernel.jdon.modulebatch.job.jd.reader.dto.PartJobDetailListInfo;
import kernel.jdon.modulebatch.job.jd.reader.dto.WantedJobDetailResponse;
import kernel.jdon.modulebatch.job.jd.reader.dto.WantedJobListResponse;
import kernel.jdon.modulebatch.job.jd.reader.fetchmanager.JobDetailFetchManager;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class WantedJdClient {
    private final RestTemplate restTemplate;
    private final ScrapingWantedProperties scrapingWantedProperties;
    private final JobDetailFetchManager jobDetailFetchManager;
    private final JobCategoryRepository jobCategoryRepository;
    private final WantedJdRepository wantedJdRepository;

    public List<WantedJobDetailResponse> getJdList(final JobSearchJobPosition jobPosition, final int offset) {
        final WantedJobListResponse jobList = fetchJobList(jobPosition, offset);
        final Set<Long> jobIdSet = jobList.toLinkedHashSet();

        return jobIdSet.stream()
            .map(jobId -> {
                WantedJobDetailResponse jobDetail = getJobDetail(jobPosition, jobId);
                jobDetailFetchManager.incrementSleepCounter();
                return jobDetail;
            })
            .toList();
    }

    public PartJobDetailListInfo getPartJdList(final JobSearchJobPosition jobPosition,
        final int offset) {
        final WantedJobListResponse jobList = fetchJobList(jobPosition, offset);
        final Set<Long> jobIdSet = jobList.toLinkedHashSet();

        return getPartJobDetailList(jobPosition, jobIdSet);
    }

    private PartJobDetailListInfo getPartJobDetailList(final JobSearchJobPosition jobPosition,
        final Set<Long> jobIdSet) {
        final List<WantedJobDetailResponse> jobDetailList = new ArrayList<>();
        int duplicateCount = 0;
        boolean isMaxDuplicate = false;
        for (Long jobDetailId : jobIdSet) {
            if (jobDetailFetchManager.isDuplicateRequired(duplicateCount)) {
                isMaxDuplicate = true;
                logReaderEnd(jobPosition, jobDetailId);
                return new PartJobDetailListInfo(isMaxDuplicate, jobDetailList); // 중복된 채용공고 스크래핑 시 Reader 종료
            }

            WantedJobDetailResponse jobDetail = getJobDetail(jobPosition, jobDetailId);

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
        WantedJobDetailResponse jobDetail = fetchJobDetail(jobDetailId);
        addJobDetailInfo(jobDetail, jobPosition);

        return jobDetail;
    }

    private void addJobDetailInfo(final WantedJobDetailResponse jobDetail, final JobSearchJobPosition jobPosition) {
        final JobCategory findJobCategory = findByJobPosition(jobPosition);

        jobDetail.addDetailInfo(scrapingWantedProperties.getDetailUrl(), findJobCategory, jobPosition);
    }

    private WantedJobListResponse fetchJobList(final JobSearchJobPosition jobPosition, final int offset) {
        final String jobListUrl = createJobListUrl(jobPosition, offset);

        return restTemplate.getForObject(jobListUrl, WantedJobListResponse.class);
    }

    private WantedJobDetailResponse fetchJobDetail(final Long jobId) {
        final String jobApiDetailUrl = scrapingWantedProperties.getApiDetailUrl();
        final String jobDetailUrl = joinToString(jobApiDetailUrl, jobId);

        return restTemplate.getForObject(jobDetailUrl, WantedJobDetailResponse.class);
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

    private JobCategory findByJobPosition(final JobSearchJobPosition jobPosition) {
        return jobCategoryRepository.findByWantedCode(jobPosition.getSearchValue())
            .orElseThrow(JobCategoryErrorCode.NOT_FOUND_JOB_CATEGORY::throwException);
    }

    private boolean isJobDetailExist(final JobCategory jobCategory, final Long jobDetailId) {
        return wantedJdRepository.existsByJobCategoryAndDetailId(jobCategory, jobDetailId);
    }

    private void logReaderEnd(final JobSearchJobPosition jobPosition, final Long jobDetailId) {
        if (jobPosition == JobSearchJobPosition.JOB_POSITION_SERVER) {
            log.info("[부분_원티드_채용공고_스크래핑_job Reader] 중복 데이터 연속된 {}개로 Reader 종료. 최종 JobDetailId = {}",
                jobDetailFetchManager.getDuplicateLimitCount(), jobDetailId);
        }
        if (jobPosition == JobSearchJobPosition.JOB_POSITION_FRONTEND) {
            log.info("[부분_원티드_채용공고_스크래핑_job Reader] 중복 데이터 연속된 {}개로 Reader 종료. 최종 JobDetailId = {}",
                jobDetailFetchManager.getDuplicateLimitCount(), jobDetailId);
        }
    }
}
