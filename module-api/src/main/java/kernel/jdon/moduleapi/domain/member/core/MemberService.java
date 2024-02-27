package kernel.jdon.moduleapi.domain.member.core;

public interface MemberService {
	MemberInfo.FindMemberResponse getMember(Long memberId);

	MemberInfo.UpdateMemberResponse modifyMember(Long memberId, MemberCommand.UpdateMemberRequest command);
}
