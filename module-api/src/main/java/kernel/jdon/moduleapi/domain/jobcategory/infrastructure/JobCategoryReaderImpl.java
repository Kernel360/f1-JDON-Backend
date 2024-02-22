package kernel.jdon.moduleapi.domain.jobcategory.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryReader;
import kernel.jdon.moduleapi.domain.jobcategory.error.JobCategoryErrorCode;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JobCategoryReaderImpl implements JobCategoryReader {
	private final JobCategoryRepository jobCategoryRepository;

	@Override
	public JobCategory findById(final Long jobCategoryId) {
		return jobCategoryRepository.findById(jobCategoryId)
			.orElseThrow(JobCategoryErrorCode.NOT_FOUND_JOB_CATEGORY::throwException);
	}
}
