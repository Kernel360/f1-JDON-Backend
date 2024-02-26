package kernel.jdon.moduleapi.domain.skill.core;

import java.util.List;

import kernel.jdon.skill.domain.Skill;

public interface SkillReader {
	List<SkillInfo.FindHotSkill> findHotSkillList();

	List<SkillInfo.FindMemberSkill> findMemberSkillList(Long memberId);

	Skill findById(Long jobCategoryId);
}
