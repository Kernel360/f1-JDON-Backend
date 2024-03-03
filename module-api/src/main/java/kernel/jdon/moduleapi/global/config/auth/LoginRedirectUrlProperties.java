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

	public String getSuccessMember() {
		return this.success.getMember();
	}

	public String getSuccessGuest() {
		return this.success.getGuest();
	}

	public String getFailureNotFoundEmail() {
		return this.failure.getNotFoundEmail();
	}

	public String getFailureNotMatchProvider() {
		return this.failure.getNotMatchProvider();
	}

	@Getter
	@RequiredArgsConstructor
	public static class Success {
		private final String member;
		private final String guest;
	}

	@Getter
	@RequiredArgsConstructor
	public static class Failure {
		private final String notFoundEmail;
		private final String notMatchProvider;
	}
}
