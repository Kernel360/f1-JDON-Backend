package kernel.jdon.modulebatch.domain.jobcategory.repository;

import java.util.Optional;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.jobcategory.repository.JobCategoryDomainRepository;

public interface JobCategoryRepository extends JobCategoryDomainRepository {
    Optional<JobCategory> findByWantedCode(String wantedCode);
}
