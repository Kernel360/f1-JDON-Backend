package kernel.jdon.moduleapi.domain.skill.core;

public interface SkillService {
    SkillInfo.FindHotSkillListResponse getHotSkillList();

    SkillInfo.FindMemberSkillListResponse getMemberSkillList(Long memberId);

    SkillInfo.FindJobCategorySkillListResponse getJobCategorySkillList(Long jobCategoryId);

    SkillInfo.FindDataListBySkillResponse getDataListBySkill(String keyword, Long userId);
}
