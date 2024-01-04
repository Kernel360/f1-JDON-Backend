package kernel.jdon.crawler.wanted.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.jobcategory.domain.JobCategory;

public interface JobCategoryRepository extends JpaRepository<JobCategory, Long> {
	Optional<JobCategory> findByWantedCode(String wantedCode);
}
