package kernel.jdon.moduleapi.domain.member.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import kernel.jdon.jobcategory.domain.JobCategory;
import kernel.jdon.member.domain.Gender;
import kernel.jdon.member.domain.Member;
import kernel.jdon.member.domain.MemberAccountStatus;
import kernel.jdon.member.domain.MemberRole;
import kernel.jdon.member.domain.SocialProviderType;
import kernel.jdon.memberskill.domain.MemberSkill;
import kernel.jdon.moduleapi.domain.auth.core.AuthCommand;
import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryReader;
import kernel.jdon.moduleapi.domain.member.core.MemberCommand;
import kernel.jdon.moduleapi.domain.member.core.MemberFactory;
import kernel.jdon.moduleapi.domain.member.core.MemberStore;
import kernel.jdon.moduleapi.domain.skill.core.SkillReader;
import kernel.jdon.skill.domain.Skill;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberFactoryImpl implements MemberFactory {
	private final JobCategoryReader jobCategoryReader;
	private final SkillReader skillReader;
	private final MemberStore memberStore;

	@Override
	public void update(final Member member, final MemberCommand.UpdateMemberRequest command) {
		final JobCategory findJobCategory = jobCategoryReader.findById(command.getJobCategoryId());
		final List<Skill> findSkillList = skillReader.findAllByIdList(command.getSkillList());
		final List<MemberSkill> updateMemberSkill = findSkillList.stream()
			.map(skill -> MemberSkill.builder()
				.member(member)
				.skill(skill)
				.build())
			.toList();
		final Member updateMember = Member.builder()
			.nickname(command.getNickname())
			.birth(command.getBirth())
			.gender(Gender.ofType(command.getGender()))
			.jobCategory(findJobCategory)
			.memberSkillList(updateMemberSkill)
			.build();

		memberStore.update(member, updateMember);
	}

	@Override
	public Member save(AuthCommand.RegisterRequest command, Map<String, String> userInfo) {
		final JobCategory findJobCategory = jobCategoryReader.findById(command.getJobCategoryId());
		final Member saveMember = Member.builder()
			.email(userInfo.get("email"))
			.nickname(command.getNickname())
			.birth(command.getBirth())
			.gender(Gender.ofType(command.getGender()))
			.role(MemberRole.ROLE_USER)
			.accountStatus(MemberAccountStatus.ACTIVE)
			.jobCategory(findJobCategory)
			.socialProvider(SocialProviderType.ofType(userInfo.get("provider")))
			.joinDate(LocalDateTime.now())
			.build();
		final Member savedMember = memberStore.save(saveMember);
		final List<Skill> findSkillList = skillReader.findAllByIdList(command.getSkillList());
		final List<MemberSkill> updateMemberSkill = findSkillList.stream()
			.map(skill -> MemberSkill.builder()
				.member(savedMember)
				.skill(skill)
				.build())
			.toList();
		savedMember.updateMemberSkillList(updateMemberSkill);

		return savedMember;
	}
}
