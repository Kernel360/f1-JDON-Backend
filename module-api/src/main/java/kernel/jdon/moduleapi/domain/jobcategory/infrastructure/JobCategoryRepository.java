package kernel.jdon.moduleapi.domain.jobcategory.infrastructure;

import java.util.List;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.jobcategory.repository.JobCategoryDomainRepository;

public interface JobCategoryRepository extends JobCategoryDomainRepository {
	List<JobCategory> findByParentIdIsNull();

	List<JobCategory> findByParentId(Long pareantId);
}
