package kernel.jdon.modulecrawler.legacy.jobcategory.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.jobcategory.repository.JobCategoryDomainRepository;

@Repository("legacyJobCategoryRepository")
public interface JobCategoryRepository extends JobCategoryDomainRepository {
    Optional<JobCategory> findByWantedCode(String wantedCode);
}
