package kernel.jdon.moduleapi.domain.auth.util;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "crypto")
public class CryptoProperties {
    private final String aesPrivateKey;
    private final String hmacPrivateKey;
}
