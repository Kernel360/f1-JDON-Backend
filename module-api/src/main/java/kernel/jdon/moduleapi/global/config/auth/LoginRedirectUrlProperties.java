package kernel.jdon.moduleapi.global.config.auth;

import static kernel.jdon.modulecommon.util.StringUtil.*;

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
		System.out.println(joinToString(referer, this.success.getMember()));
		return joinToString(referer, this.success.getMember());
	}

	public String getSuccessGuest(String referer) {
		return joinToString(referer, this.success.getGuest());
	}

	public String getFailureNotFoundEmail(String referer) {
		return joinToString(referer, this.failure.getNotFoundEmail());
	}

	public String getFailureNotMatchProvider(String referer) {
		return joinToString(referer, this.failure.getNotMatchProvider());
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
