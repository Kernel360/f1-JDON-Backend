package kernel.jdon.jobcategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.jobcategory.domain.JobCategory;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {
}
