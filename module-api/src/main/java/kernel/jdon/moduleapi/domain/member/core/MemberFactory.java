package kernel.jdon.moduleapi.domain.member.core;

import kernel.jdon.member.domain.Member;

public interface MemberFactory {
	Member toUpdateMember(Member member, MemberCommand.UpdateMemberRequest command);
}
