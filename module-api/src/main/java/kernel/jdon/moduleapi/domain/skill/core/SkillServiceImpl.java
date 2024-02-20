package kernel.jdon.moduleapi.domain.skill.core;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryReader;
import kernel.jdon.skill.domain.SkillType;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
	private final SkillReader skillReader;
	private final JobCategoryReader jobCategoryReader;

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

	@Override
	public SkillInfo.FindJobCategorySkillListResponse getJobCategorySkillList(final Long jobCategoryId) {
		JobCategory findJobCategory = jobCategoryReader.findById(jobCategoryId);
		final List<SkillInfo.FindJobCategorySkill> jobCategorySkillList = findJobCategory.getSkillList().stream()
			.filter(skill -> !skill.getKeyword().equals(SkillType.getOrderKeyword()))
			.map(skill -> new SkillInfo.FindJobCategorySkill(skill.getId(), skill.getKeyword()))
			.toList();

		// todo : 서버에서 루프를 통해서 '기타' 인것을 제외할지 아래처럼 sql질의로 제외할지 멘토링 필요
		// final List<SkillInfo.FindJobCategorySkill> jobCategorySkillList = skillReader.findJobCategorySkillList(jobCategoryId);

		return new SkillInfo.FindJobCategorySkillListResponse(jobCategorySkillList);
	}
}
