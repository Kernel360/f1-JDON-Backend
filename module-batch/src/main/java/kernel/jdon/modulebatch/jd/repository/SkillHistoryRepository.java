package kernel.jdon.modulebatch.jd.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import kernel.jdon.moduledomain.skillhistory.repository.SkillHistoryDomainRepository;

public interface SkillHistoryRepository extends SkillHistoryDomainRepository {
    @Modifying
    @Query("delete from SkillHistory s where s.wantedJd.id = :wantedJdId")
    void deleteAllByWantedJdId(Long wantedJdId);
}
