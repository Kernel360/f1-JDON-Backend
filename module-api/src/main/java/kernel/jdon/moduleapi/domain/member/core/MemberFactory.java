package kernel.jdon.moduleapi.domain.member.core;

import java.util.Map;

import kernel.jdon.member.domain.Member;

public interface MemberFactory {
	void update(Member member, MemberCommand.UpdateMemberRequest command);

	Member save(MemberCommand.RegisterRequest command, Map<String, String> userInfo);
}
