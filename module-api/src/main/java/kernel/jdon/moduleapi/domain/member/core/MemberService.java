package kernel.jdon.moduleapi.domain.member.core;

public interface MemberService {
	MemberInfo.FindMemberResponse find(Long memberId);
}
