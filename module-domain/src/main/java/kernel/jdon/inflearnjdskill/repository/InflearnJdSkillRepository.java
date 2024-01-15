package kernel.jdon.inflearnjdskill.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kernel.jdon.inflearnjdskill.domain.InflearnJdSkill;
import kernel.jdon.skill.domain.Skill;

public interface InflearnJdSkillRepository extends JpaRepository<InflearnJdSkill, Long> {

	@Query("select i from InflearnJdSkill i where i.wantedJdSkill.skill = :skill")
	List<InflearnJdSkill> findBySkill(Skill skill);
}
