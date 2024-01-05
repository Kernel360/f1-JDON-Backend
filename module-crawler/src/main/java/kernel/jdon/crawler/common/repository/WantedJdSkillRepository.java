package kernel.jdon.crawler.common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.skill.domain.Skill;
import kernel.jdon.wantedskill.domain.WantedJdSkill;

public interface WantedJdSkillRepository extends JpaRepository<WantedJdSkill, Long> {

	List<WantedJdSkill> findBySkill(Skill skill);

}
