package kernel.jdon.moduleapi.domain.auth.core;

import kernel.jdon.moduleapi.domain.member.core.MemberCommand;

public interface CustomOAuth2UserService {
	boolean sendDeleteRequestToOAuth2(final MemberCommand.WithdrawRequest command);
}
