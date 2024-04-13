package kernel.jdon.modulecrawler.legacy.wanted.repository;

import org.springframework.stereotype.Repository;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.wantedjd.repository.WantedJdDomainRepository;

@Repository("legacyWantedJdRepository")
public interface WantedJdRepository extends WantedJdDomainRepository {
    boolean existsByJobCategoryAndDetailId(JobCategory jobCategory, Long detailId);
}
