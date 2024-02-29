package kernel.jdon.modulecrawler.wanted.repository;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.wantedjd.repository.WantedJdDomainRepository;

public interface WantedJdRepository extends WantedJdDomainRepository {
	boolean existsByJobCategoryAndDetailId(JobCategory jobCategory, Long detailId);
}
