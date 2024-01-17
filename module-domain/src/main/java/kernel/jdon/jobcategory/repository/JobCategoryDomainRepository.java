package kernel.jdon.jobcategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.jobcategory.domain.JobCategory;

public interface JobCategoryDomainRepository extends JpaRepository<JobCategory, Long> {
}
