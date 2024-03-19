package kernel.jdon.modulebatch.domain.wantedjdskill.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import kernel.jdon.moduledomain.wantedjdskill.repository.WantedJdSkillDomainRepository;

public interface WantedJdSkillRepository extends WantedJdSkillDomainRepository {
    @Modifying
    @Query("delete from WantedJdSkill ws where ws.wantedJd.id = :wantedJdId")
    void deleteAllByWantedJdId(Long wantedJdId);
}
