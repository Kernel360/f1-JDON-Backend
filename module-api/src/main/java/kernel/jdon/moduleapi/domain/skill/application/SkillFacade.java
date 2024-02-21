package kernel.jdon.moduleapi.domain.skill.application;

import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.skill.core.SkillInfo;
import kernel.jdon.moduleapi.domain.skill.core.SkillService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillFacade {
	private final SkillService skillService;

	public SkillInfo.FindHotSkillListResponse getHotSkillList() {
		return skillService.getHotSkillList();
	}

	public SkillInfo.FindMemberSkillListResponse getMemberSkillList(final Long memberId) {
		return skillService.getMemberSkillList(memberId);
	}

	public SkillInfo.FindJobCategorySkillListResponse getJobCategorySkillList(final Long jobCategoryId) {
		return skillService.getJobCategorySkillList(jobCategoryId);
	}

	public SkillInfo.FindDataListBySkillResponse getDataListBySkill(final String keyword, final Long memberId) {
		return skillService.getDataListBySkill(keyword, memberId);
	}
}
