package kernel.jdon.moduleapi.domain.auth.core;

import org.springframework.security.oauth2.core.user.OAuth2User;

import kernel.jdon.member.domain.SocialProviderType;
import kernel.jdon.moduleapi.domain.auth.error.AuthErrorCode;
import kernel.jdon.moduleapi.domain.member.core.MemberCommand;
import kernel.jdon.moduleapi.global.dto.SessionUserInfo;
import kernel.jdon.moduleapi.global.exception.AuthException;

public interface OAuth2Strategy {

	SocialProviderType getOAuth2ProviderType();

	SessionUserInfo getUserInfo(OAuth2User user);

	boolean unlinkOAuth2Account(MemberCommand.WithdrawRequest command);

	default void isEmailExist(String email) {
		if (null == email) {
			throw new AuthException(AuthErrorCode.UNAUTHORIZED_OAUTH_RETURN_NULL_EMAIL);
		}
	}
}
