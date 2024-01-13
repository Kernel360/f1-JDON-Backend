package kernel.jdon.skill.repository;

import java.util.List;

import kernel.jdon.skill.domain.Skill;
import kernel.jdon.skill.dto.FindHotSkillResponse;

public interface SkillRepositoryCustom {
	List<String> findHotSkillList();
}
