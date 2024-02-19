package kernel.jdon.moduleapi.domain.skill.application;

import java.util.List;

import kernel.jdon.moduleapi.domain.skill.core.SkillInfo;

public interface SkillReader {
	List<SkillInfo.FindHotSkill> findHotSkillList();

	List<SkillInfo.FindMemberSkill> findMemberSkillList(final Long memberId);
}
