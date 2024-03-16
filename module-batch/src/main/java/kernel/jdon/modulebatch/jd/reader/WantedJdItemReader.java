package kernel.jdon.modulebatch.jd.reader;

import static kernel.jdon.modulecommon.util.StringUtil.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import kernel.jdon.modulebatch.config.ScrapingWantedProperties;
import kernel.jdon.modulebatch.jd.reader.dto.WantedJobDetailListResponse;
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
public class WantedJdItemReader implements ItemReader<WantedJobDetailListResponse> {
    private final RestTemplate restTemplate;
    private final ScrapingWantedProperties scrapingWantedProperties;
    private final JobCategoryRepository jobCategoryRepository;
    private final Integer offset;

    @Override
    public WantedJobDetailListResponse read() throws
        Exception,
        UnexpectedInputException,
        ParseException,
        NonTransientResourceException {
        // todo : 외부에서 변경되어 JobParameter로 전달되는 jobPosition && offset
        JobSearchJobPosition jobPosition = JobSearchJobPosition.JOB_POSITION_SERVER;
        int offset = 10;

        final JobCategory findJobCategory = findByJobPosition(jobPosition);
        final WantedJobListResponse jobList = fetchJobList(jobPosition, offset);
        final Set<Long> jobIdSet = jobList.getData().stream()
            .map(WantedJobListResponse.Data::getId)
            .collect(Collectors.toCollection(LinkedHashSet::new));
        final List<WantedJobDetailResponse> jobDetailList = jobIdSet.stream()
            .map(jobId -> getJobDetail(findJobCategory, jobId))
            .toList();

        return jobDetailList.size() > 0 ? new WantedJobDetailListResponse(jobDetailList) : null;
    }

    private WantedJobDetailResponse getJobDetail(final JobCategory jobCategory, final Long jobDetailId) {
        WantedJobDetailResponse jobDetail = fetchJobDetail(jobDetailId);
        addJobDetailInfo(jobDetail, jobCategory, jobDetailId);

        return jobDetail;
    }

    private void addJobDetailInfo(final WantedJobDetailResponse jobDetail, final JobCategory jobCategory,
        final Long jobDetailId) {
        final String jobUrlDetail = scrapingWantedProperties.getDetailUrl();
        jobDetail.addDetailInfo(joinToString(jobUrlDetail, jobDetailId), jobCategory);
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
