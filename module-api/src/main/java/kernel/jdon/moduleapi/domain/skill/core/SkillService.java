package kernel.jdon.moduleapi.domain.skill.core;

public interface SkillService {
	SkillInfo.FindHotSkillListResponse getHotSkillList();

	SkillInfo.FindMemberSkillListResponse getMemberSkillList(final Long memberId);

	SkillInfo.FindJobCategorySkillListResponse getJobCategorySkillList(final Long jobCategoryId);
}
