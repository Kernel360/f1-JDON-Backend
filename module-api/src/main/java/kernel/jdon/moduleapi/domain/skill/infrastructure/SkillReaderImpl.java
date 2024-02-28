package kernel.jdon.moduleapi.domain.skill.infrastructure;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.skill.core.SkillInfo;
import kernel.jdon.moduleapi.domain.skill.core.SkillReader;
import kernel.jdon.moduleapi.domain.skill.error.SkillErrorCode;
import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduledomain.skill.domain.Skill;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SkillReaderImpl implements SkillReader {
	private final SkillRepository skillRepository;
	private final SkillReaderInfoMapper skillReaderInfoMapper;

	@Override
	public List<SkillInfo.FindHotSkill> findHotSkillList() {
		final List<SkillReaderInfo.FindHotSkill> hotSkillList = skillRepository.findHotSkillList();
		return hotSkillList.stream()
			.map(skillReaderInfoMapper::of)
			.toList();
	}

	@Override
	public List<SkillInfo.FindMemberSkill> findMemberSkillList(final Long memberId) {
		final List<SkillReaderInfo.FindMemberSkill> memberSkillList = skillRepository.findMemberSkillList(memberId);
		return memberSkillList.stream()
			.map(skillReaderInfoMapper::of)
			.toList();
	}

	@Override
	public Skill findById(Long jobCategoryId) {
		return skillRepository.findById(jobCategoryId)
			.orElseThrow(SkillErrorCode.NOT_FOUND_SKILL::throwException);
	}

	@Override
	public List<Skill> findAllByIdList(List<Long> skillIdList) {
		List<Skill> skillList = skillRepository.findAllById(skillIdList);
		if (skillList.size() < skillIdList.size()) {
			throw new ApiException(SkillErrorCode.NOT_FOUND_SKILL);
		}
		return skillList;
	}
}
