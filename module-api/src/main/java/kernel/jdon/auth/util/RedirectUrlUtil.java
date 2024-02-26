package kernel.jdon.auth.util;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.global.config.auth.AllowOriginProperties;
import kernel.jdon.moduleapi.global.config.auth.LoginRedirectUrlProperties;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedirectUrlUtil {
	private final AllowOriginProperties allowOriginProperties;
	private final LoginRedirectUrlProperties loginRedirectUrlProperties;

	public String getLoginSuccessMember(String referer) {
		referer = isReferer(referer);
		return this.loginRedirectUrlProperties.getSuccessMember(referer);
	}

	public String getLoginSuccessGuest(String referer) {
		referer = isReferer(referer);
		return this.loginRedirectUrlProperties.getSuccessGuest(referer);
	}

	public String getLoginFailureNotFoundEmail(String referer) {
		referer = isReferer(referer);
		return this.loginRedirectUrlProperties.getFailureNotFoundEmail(referer);
	}

	public String getLoginFailureNotMatchProvider(String referer) {
		referer = isReferer(referer);
		return this.loginRedirectUrlProperties.getFailureNotMatchProvider(referer);
	}

	public String getLogoutSuccess(String referer) {
		referer = isReferer(referer);
		return referer;
	}

	private String isReferer(String referer) {
		if (referer == null) {
			referer = allowOriginProperties.getOrigin();
		}
		return referer;
	}
}
