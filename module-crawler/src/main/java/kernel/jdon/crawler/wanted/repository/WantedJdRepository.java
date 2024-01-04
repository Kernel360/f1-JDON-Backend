package kernel.jdon.crawler.wanted.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.wanted.domain.WantedJd;

public interface WantedJdRepository extends JpaRepository<WantedJd, Long> {
	boolean existsByJobCategoryAndDetailId(JobCategory jobCategory, Long detailId);

}
