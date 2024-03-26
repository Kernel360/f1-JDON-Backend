package kernel.jdon.modulebatch.domain.wantedjd.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import kernel.jdon.moduledomain.wantedjd.repository.WantedJdDomainRepository;

public interface WantedJdRepository extends WantedJdDomainRepository {
    WantedJd findByJobCategoryAndDetailId(JobCategory jobCategory, Long detailId);

    boolean existsByJobCategoryAndDetailId(JobCategory jobCategory, Long detailId);

    @Modifying
    @Query(value = "update wanted_jd set active_status = 'CLOSE' "
        + "where DATE_FORMAT(modified_date, '%Y-%m-%d') != DATE_FORMAT(now(), '%Y-%m-%d') "
        + "or modified_date is null", nativeQuery = true)
    void updateWantedJdActiveStatus();
}
