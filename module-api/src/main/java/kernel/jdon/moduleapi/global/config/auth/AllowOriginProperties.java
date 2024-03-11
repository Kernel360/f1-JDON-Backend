package kernel.jdon.moduleapi.global.config.auth;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "allowed-origins")
public class AllowOriginProperties {
    private final String origin;
    private final List<String> url;
    private final List<String> monitoring;
}
