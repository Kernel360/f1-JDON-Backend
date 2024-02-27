package kernel.jdon.moduleapi.domain.auth.infrastructure;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import kernel.jdon.member.domain.SocialProviderType;
import kernel.jdon.moduleapi.domain.auth.core.OAuth2Strategy;
import kernel.jdon.moduleapi.domain.auth.dto.SessionUserInfo;
import kernel.jdon.moduleapi.domain.auth.dto.UserInfoFromOAuth2;

@Component
public class GithubOAuth2StrategyImpl implements OAuth2Strategy {
	@Override
	public SocialProviderType getOAuth2ProviderType() {
		return SocialProviderType.GITHUB;
	}

	@Override
	public UserInfoFromOAuth2 getUserInfo(final OAuth2User user) {
		final String email = (String)user.getAttributes().get("email");
		isEmailExist(email);
		final String oAuthId = String.valueOf(user.getAttributes().get("id"));

		return UserInfoFromOAuth2.of(email, oAuthId, SocialProviderType.GITHUB);

	}

	@Override
	public boolean unlinkOAuth2Account(final SessionUserInfo userInfo) {
		return false;
	}
}
