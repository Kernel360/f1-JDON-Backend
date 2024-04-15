package kernel.jdon.modulecrawler.domain.jd.infrastructure;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import kernel.jdon.moduledomain.wantedjd.repository.WantedJdDomainRepository;

public interface WantedJdRepository extends WantedJdDomainRepository {

    boolean existsByJobCategoryAndDetailId(JobCategory jobCategory, Long detailId);

    WantedJd findByJobCategoryAndDetailId(JobCategory jobCategory, Long jobDetailId);
}
