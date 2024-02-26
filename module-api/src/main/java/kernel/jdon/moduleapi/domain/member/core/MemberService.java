package kernel.jdon.moduleapi.domain.member.core;

public interface MemberService {
	MemberInfo.FindMemberResponse find(Long memberId);

	MemberInfo.UpdateMemberResponse update(Long memberId, MemberCommand.UpdateMemberRequest command);
}
