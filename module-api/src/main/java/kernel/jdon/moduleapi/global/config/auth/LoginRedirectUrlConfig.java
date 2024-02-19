package kernel.jdon.moduleapi.global.config.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "redirect-url.login")
public class LoginRedirectUrlConfig {
	private final Success success;

	@Getter
	@RequiredArgsConstructor
	public static class Success {
		private final String member;
		private final String guest;
	}
}
