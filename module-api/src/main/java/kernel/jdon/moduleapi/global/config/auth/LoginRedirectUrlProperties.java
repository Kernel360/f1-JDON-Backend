package kernel.jdon.moduleapi.global.config.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "redirect-url.login")
public class LoginRedirectUrlProperties {
	private final Success success;
	private final Failure failure;

	public String getSuccessMember(String referer) {
		if (requestFromLocal(referer)) {
			return this.success.getLocalMember();
		}
		return this.success.getMember();
	}

	public String getSuccessGuest(String referer) {
		if (requestFromLocal(referer)) {
			return this.success.getLocalGuest();
		}
		return this.success.getGuest();
	}

	public String getFailureNotFoundEmail(String referer) {
		if (requestFromLocal(referer)) {
			return this.failure.getLocalNotFoundEmail();
		}
		return this.failure.getNotFoundEmail();
	}

	public String getFailureNotMatchProvider(String referer) {
		if (requestFromLocal(referer)) {
			return this.failure.getLocalNotMatchProvider();
		}
		return this.failure.getNotMatchProvider();
	}

	private boolean requestFromLocal(String referer) {
		return referer.contains("localhost:3000");
	}

	@Getter
	@RequiredArgsConstructor
	public static class Success {
		private final String member;
		private final String localMember;
		private final String guest;
		private final String localGuest;
	}

	@Getter
	@RequiredArgsConstructor
	public static class Failure {
		private final String notFoundEmail;
		private final String localNotFoundEmail;
		private final String notMatchProvider;
		private final String localNotMatchProvider;
	}
}
