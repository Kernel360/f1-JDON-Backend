package kernel.jdon.moduleapi.domain.member.core;

import java.util.Map;

import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.auth.core.AuthCommand;

public interface MemberFactory {
	void update(Member member, MemberCommand.UpdateMemberRequest command);

	Member save(AuthCommand.RegisterRequest command, Map<String, String> userInfo);
}
