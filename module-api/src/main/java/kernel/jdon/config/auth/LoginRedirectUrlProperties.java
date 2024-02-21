package kernel.jdon.config.auth;

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

	public String getSuccessLocalMember() {
		return this.success.getLocalMember();
	}

	public String getSuccessGuest() {
		return this.success.getGuest();
	}

	public String getSuccessLocalGuest() {
		return this.success.getLocalGuest();
	}

	public String getFailureNotFoundEmail() {
		return this.failure.getNotFoundEmail();
	}

	public String getFailureLocalNotFoundEmail() {
		return this.failure.getLocalNotFoundEmail();
	}

	public String getFailureNotMatchProvider() {
		return this.failure.getNotMatchProvider();
	}

	public String getFailureLocalNotMatchProvider() {
		return this.failure.getLocalNotMatchProvider();
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
