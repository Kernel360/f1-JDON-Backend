package kernel.jdon.modulebatch.domain.inflearnjdskill.repository;

import java.util.List;

import kernel.jdon.moduledomain.inflearnjdskill.domain.InflearnJdSkill;
import kernel.jdon.moduledomain.inflearnjdskill.repository.InflearnJdSkillDomainRepository;
import kernel.jdon.moduledomain.skill.domain.Skill;

public interface InflearnJdSkillRepository extends InflearnJdSkillDomainRepository {
    List<InflearnJdSkill> findBySkill(Skill skill);
}
