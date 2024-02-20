package kernel.jdon.moduleapi.domain.jobcategory.core;

import kernel.jdon.jobcategory.domain.JobCategory;

public interface JobCategoryReader {
	JobCategory findById(final Long jobCategoryId);
}
