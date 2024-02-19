package kernel.jdon.moduleapi.domain.skill.core;

public interface SkillService {
	SkillInfo.FindHotSkillListResponse getHotSkillList();

	SkillInfo.FinMemberSkillListResponse getMemberSkillList(final Long memberId);
}
