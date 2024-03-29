package kernel.jdon.moduleapi.global.config.auth.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "allowed-origins")
public class AllowOriginProperties {
    private final String origin;
    private final List<String> urlList;
    private final Ip ip;

    public List<String> getAllowIpMonitoringList() {
        return this.ip.monitoringList;
    }

    @Getter
    @RequiredArgsConstructor
    public static class Ip {
        private final List<String> monitoringList;
    }
}
