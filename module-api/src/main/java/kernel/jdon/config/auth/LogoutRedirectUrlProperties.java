package kernel.jdon.config.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "redirect-url.logout")
public class LogoutRedirectUrlProperties {
	private final String success;
	private final String localSuccess;
}
