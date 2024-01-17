package kernel.jdon.inflearnjdskill.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.inflearnjdskill.domain.InflearnJdSkill;
import kernel.jdon.skill.domain.Skill;

public interface InflearnJdSkillRepository extends JpaRepository<InflearnJdSkill, Long> {
	
	List<InflearnJdSkill> findBySkill(Skill skill);
}
