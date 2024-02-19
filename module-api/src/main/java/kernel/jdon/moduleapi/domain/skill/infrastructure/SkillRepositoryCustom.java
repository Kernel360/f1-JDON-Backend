package kernel.jdon.moduleapi.domain.skill.infrastructure;

import java.util.List;

public interface SkillRepositoryCustom {
	List<SkillReaderInfo.FindHotSkill> findHotSkillList();

	List<SkillReaderInfo.FindMemberSkill> findMemberSkillList(final Long memberId);
}
