package kernel.jdon.modulebatch.jd.reader;

import static kernel.jdon.modulecommon.util.StringUtil.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import kernel.jdon.modulebatch.config.ScrapingWantedProperties;
import kernel.jdon.modulebatch.jd.reader.counter.JobDetailFetchManager;
import kernel.jdon.modulebatch.jd.reader.dto.WantedJobDetailResponse;
import kernel.jdon.modulebatch.jd.reader.dto.WantedJobListResponse;
import kernel.jdon.modulebatch.jd.repository.JobCategoryRepository;
import kernel.jdon.modulebatch.jd.search.JobSearchExperience;
import kernel.jdon.modulebatch.jd.search.JobSearchJobCategory;
import kernel.jdon.modulebatch.jd.search.JobSearchJobPosition;
import kernel.jdon.modulebatch.jd.search.JobSearchLocation;
import kernel.jdon.modulebatch.jd.search.JobSearchSort;
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

    public List<WantedJobDetailResponse> getJobDetailList(final JobSearchJobPosition jobPosition, final int offset) {
        final WantedJobListResponse jobList = fetchJobList(jobPosition, offset);

        final Set<Long> jobIdSet = jobList.getData().stream()
            .map(WantedJobListResponse.Data::getId)
            .collect(Collectors.toCollection(LinkedHashSet::new));

        return jobIdSet.stream()
            .map(jobId -> {
                WantedJobDetailResponse jobDetail = getJobDetail(jobPosition, jobId);
                jobDetailFetchManager.incrementSleepCounter();
                return jobDetail;
            })
            .toList();
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
        final int maxFetchJDListOffset = scrapingWantedProperties.getMaxFetchJdListOffset();
        final String jobApiListUrl = scrapingWantedProperties.getApiListUrl();

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

    private JobCategory findByJobPosition(final JobSearchJobPosition jobPosition) {
        return jobCategoryRepository.findByWantedCode(jobPosition.getSearchValue())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 잡 카테고리")); // todo 에러코드 변경 필요
    }
}
