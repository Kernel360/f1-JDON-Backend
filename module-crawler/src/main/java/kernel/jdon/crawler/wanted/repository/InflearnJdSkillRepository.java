package kernel.jdon.crawler.wanted.repository;

import java.util.List;

import kernel.jdon.inflearnjdskill.domain.InflearnJdSkill;
import kernel.jdon.inflearnjdskill.repository.InflearnJdSkillDomainRepository;
import kernel.jdon.skill.domain.Skill;

public interface InflearnJdSkillRepository extends InflearnJdSkillDomainRepository {
	List<InflearnJdSkill> findBySkill(Skill skill);
}
