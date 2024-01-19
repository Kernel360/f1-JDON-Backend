package kernel.jdon.jobcategory.repository;

import java.util.List;

import kernel.jdon.jobcategory.domain.JobCategory;

public interface JobCategoryRepository extends JobCategoryDomainRepository {
	List<JobCategory> findByParentIdIsNull();
	List<JobCategory> findByParentId(Long pareantId);
}
