package kernel.jdon.jobcategory.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import kernel.jdon.jobcategory.domain.JobCategory;

@Repository("legacyJobCategoryRepository")
public interface JobCategoryRepository extends JobCategoryDomainRepository {
	List<JobCategory> findByParentIdIsNull();

	List<JobCategory> findByParentId(Long pareantId);
}
