package kernel.jdon.moduleapi.global.config.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ConfigurationProperties(prefix = "redirect-url.logout")
public class LogoutRedirectUrlProperties {
	private final String success;
	private final String localSuccess;

	public String getSuccess(String referer) {
		if (referer.contains("localhost:3000")) {
			return this.localSuccess;
		}
		return this.success;
	}
}
