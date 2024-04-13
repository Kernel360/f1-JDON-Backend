package kernel.jdon.modulecrawler.domain.jd.core;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;

public interface JdReader {
    boolean existsByJobCategoryAndDetailId(JobCategory jobCategory, Long detailId);

    WantedJd findByJobCategoryAndDetailId(JobCategory jobCategory, Long jobDetailId);
}
