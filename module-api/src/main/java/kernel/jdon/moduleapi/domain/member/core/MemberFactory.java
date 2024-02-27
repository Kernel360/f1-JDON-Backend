package kernel.jdon.moduleapi.domain.member.core;

import kernel.jdon.member.domain.Member;

public interface MemberFactory {
	void update(Member member, MemberCommand.UpdateMemberRequest command);
}
