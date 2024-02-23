package kernel.jdon.moduleapi.domain.jobcategory.core;

import java.util.List;

import kernel.jdon.jobcategory.domain.JobCategory;

public interface JobCategoryReader {
	JobCategory findById(final Long jobCategoryId);

	List<JobCategory> findByParentIdIsNull();

	List<JobCategory> findByParentId(final Long parentId);
}
