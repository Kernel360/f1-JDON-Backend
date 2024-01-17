package kernel.jdon.crawler.wanted.repository;

import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.wantedjd.repository.WantedJdDomainRepository;

public interface WantedJdRepository extends WantedJdDomainRepository {
	boolean existsByJobCategoryAndDetailId(JobCategory jobCategory, Long detailId);
}
