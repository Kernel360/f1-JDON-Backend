package kernel.jdon.crawler.common.repository;

import java.util.List;

import kernel.jdon.skill.domain.Skill;

public interface WantedJdSkillRepositoryCustom {

	List<Skill> findAllSkills();
}
