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

	public SkillInfo.FinMemberSkillListResponse getMemberSkillList(final Long memberId) {
		return skillService.getMemberSkillList(memberId);
	}
}
