package kernel.jdon.moduleapi.domain.skill.core;

import java.util.List;

public interface SkillReader {
	List<SkillInfo.FindHotSkill> findHotSkillList();

	List<SkillInfo.FindMemberSkill> findMemberSkillList(final Long memberId);
}
