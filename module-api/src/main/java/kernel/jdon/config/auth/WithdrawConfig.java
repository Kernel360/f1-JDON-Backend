package kernel.jdon.config.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "custom-oauth2.kakao")
public class WithdrawConfig {
	private final String appAdminKey;
	private final String deleteUserUrl;
}