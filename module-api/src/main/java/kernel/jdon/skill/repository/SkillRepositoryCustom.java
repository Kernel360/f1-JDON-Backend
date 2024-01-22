package kernel.jdon.skill.repository;

import java.util.List;

import kernel.jdon.skill.dto.object.FindHotSkillDto;
import kernel.jdon.skill.dto.object.FindMemberSkillDto;

public interface SkillRepositoryCustom {
	List<FindHotSkillDto> findHotSkillList();

	List<FindMemberSkillDto> findMemberSkillList(Long memberId);
}
