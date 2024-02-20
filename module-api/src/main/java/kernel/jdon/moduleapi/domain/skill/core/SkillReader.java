package kernel.jdon.moduleapi.domain.skill.core;

import java.util.List;

import kernel.jdon.skill.domain.Skill;

public interface SkillReader {
	List<SkillInfo.FindHotSkill> findHotSkillList();

	List<SkillInfo.FindMemberSkill> findMemberSkillList(final Long memberId);

	List<SkillInfo.FindJobCategorySkill> findJobCategorySkillList(final Long jobCategoryId);

	Skill findById(final Long jobCategoryId);
}
