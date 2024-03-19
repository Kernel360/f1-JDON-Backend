package kernel.jdon.modulebatch.domain.wantedjd.repository;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import kernel.jdon.moduledomain.wantedjd.repository.WantedJdDomainRepository;

public interface WantedJdRepository extends WantedJdDomainRepository {
    WantedJd findByJobCategoryAndDetailId(JobCategory jobCategory, Long detailId);
}
