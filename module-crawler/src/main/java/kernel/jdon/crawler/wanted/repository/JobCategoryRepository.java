package kernel.jdon.crawler.wanted.repository;

import java.util.Optional;

import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.jobcategory.repository.JobCategoryDomainRepository;

public interface JobCategoryRepository extends JobCategoryDomainRepository {
	Optional<JobCategory> findByWantedCode(String wantedCode);
}
