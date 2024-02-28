package kernel.jdon.moduledomain.jobcategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;

public interface JobCategoryDomainRepository extends JpaRepository<JobCategory, Long> {
}
