package kernel.jdon.moduleapi.domain.auth.infrastructure;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import kernel.jdon.moduledomain.member.domain.SocialProviderType;
import kernel.jdon.moduleapi.domain.auth.core.OAuth2Strategy;
import kernel.jdon.moduleapi.domain.member.core.MemberCommand;
import kernel.jdon.moduleapi.global.dto.SessionUserInfo;

@Component
public class GithubOAuth2StrategyImpl implements OAuth2Strategy {
	@Override
	public SocialProviderType getOAuth2ProviderType() {
		return SocialProviderType.GITHUB;
	}

	@Override
	public SessionUserInfo getUserInfo(final OAuth2User user) {
		final String email = (String)user.getAttributes().get("email");
		isEmailExist(email);
		final String oAuthId = String.valueOf(user.getAttributes().get("id"));

		return SessionUserInfo.of(email, oAuthId, SocialProviderType.GITHUB);
	}

	@Override
	public boolean unlinkOAuth2Account(final MemberCommand.WithdrawRequest command) {
		// todo 구현
		return true;
	}
}
