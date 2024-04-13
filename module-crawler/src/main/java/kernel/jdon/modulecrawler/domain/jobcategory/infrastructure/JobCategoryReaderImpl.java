package kernel.jdon.modulecrawler.domain.jobcategory.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.modulecrawler.domain.jobcategory.core.JobCategoryReader;
import kernel.jdon.modulecrawler.domain.jobcategory.error.JobCategoryErrorCode;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JobCategoryReaderImpl implements JobCategoryReader {
    private final JobCategoryRepository jobCategoryRepository;

    @Override
    public JobCategory findByWantedCode(final String wantedCode) {
        return jobCategoryRepository.findByWantedCode(wantedCode)
            .orElseThrow(JobCategoryErrorCode.NOT_FOUND_JOB_CATEGORY::throwException);
    }
}
