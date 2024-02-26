package kernel.jdon.moduleapi.domain.skill.core;

import java.util.List;

import kernel.jdon.skill.domain.Skill;

public interface SkillReader {
	List<SkillInfo.FindHotSkill> findHotSkillList();

	List<SkillInfo.FindMemberSkill> findMemberSkillList(final Long memberId);

	Skill findById(final Long jobCategoryId);

	List<Skill> findAllById(List<Long> skillList);
}
