package kernel.jdon.skill.repository;

import java.util.List;

import kernel.jdon.skill.dto.object.FindHotSkillDto;

public interface SkillRepositoryCustom {
	List<FindHotSkillDto> findHotSkillList();
}
