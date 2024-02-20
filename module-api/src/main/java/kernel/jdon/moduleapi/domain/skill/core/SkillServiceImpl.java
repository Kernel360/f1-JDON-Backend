package kernel.jdon.moduleapi.domain.skill.core;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
	private final SkillReader skillReader;

	@Override
	public SkillInfo.FindHotSkillListResponse getHotSkillList() {
		final List<SkillInfo.FindHotSkill> hotSkillList = skillReader.findHotSkillList();
		return new SkillInfo.FindHotSkillListResponse(hotSkillList);
	}

	@Override
	public SkillInfo.FindMemberSkillListResponse getMemberSkillList(final Long memberId) {
		final List<SkillInfo.FindMemberSkill> memberSkillList = skillReader.findMemberSkillList(memberId);
		return new SkillInfo.FindMemberSkillListResponse(memberSkillList);
	}
}
