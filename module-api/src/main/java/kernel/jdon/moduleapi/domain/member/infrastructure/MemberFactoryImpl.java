package kernel.jdon.moduleapi.domain.member.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryReader;
import kernel.jdon.moduleapi.domain.member.core.MemberCommand;
import kernel.jdon.moduleapi.domain.member.core.MemberFactory;
import kernel.jdon.moduleapi.domain.member.core.MemberStore;
import kernel.jdon.moduleapi.domain.skill.core.SkillReader;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.member.domain.Gender;
import kernel.jdon.moduledomain.member.domain.Member;
import kernel.jdon.moduledomain.member.domain.MemberAccountStatus;
import kernel.jdon.moduledomain.member.domain.MemberRole;
import kernel.jdon.moduledomain.member.domain.SocialProviderType;
import kernel.jdon.moduledomain.memberskill.domain.MemberSkill;
import kernel.jdon.moduledomain.skill.domain.Skill;
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
        final Member initUpdateMember = initMemberForUpdate(command, findJobCategory);
        final List<MemberSkill> updateMemberSkill = findSkillList.stream()
            .map(skill -> MemberSkill.builder()
                .member(member)
                .skill(skill)
                .build())
            .toList();
        initUpdateMember.updateMemberSkillList(updateMemberSkill);

        memberStore.update(member, initUpdateMember);
    }

    private Member initMemberForUpdate(final MemberCommand.UpdateMemberRequest command, final JobCategory jobCategory) {
        return Member.builder()
            .nickname(command.getNickname())
            .birth(command.getBirth())
            .gender(Gender.ofType(command.getGender()))
            .jobCategory(jobCategory)
            .build();
    }

    @Override
    public Member save(final MemberCommand.RegisterRequest command, final Map<String, String> userInfo) {
        final JobCategory findJobCategory = jobCategoryReader.findById(command.getJobCategoryId());
        final Member initMember = initMemberForSave(command, userInfo, findJobCategory);
        final Member saveMember = memberStore.save(initMember);
        final List<Skill> findSkillList = skillReader.findAllByIdList(command.getSkillList());
        final List<MemberSkill> updateMemberSkill = findSkillList.stream()
            .map(skill -> MemberSkill.builder()
                .member(saveMember)
                .skill(skill)
                .build())
            .toList();
        saveMember.updateMemberSkillList(updateMemberSkill);

        return saveMember;
    }

    private Member initMemberForSave(final MemberCommand.RegisterRequest command, final Map<String, String> userInfo,
        final JobCategory jobCategory) {

        return Member.builder()
            .email(userInfo.get("email"))
            .nickname(command.getNickname())
            .birth(command.getBirth())
            .gender(Gender.ofType(command.getGender()))
            .role(MemberRole.ROLE_USER)
            .accountStatus(MemberAccountStatus.ACTIVE)
            .jobCategory(jobCategory)
            .socialProvider(SocialProviderType.ofType(userInfo.get("provider")))
            .joinDate(LocalDateTime.now())
            .build();
    }
}
