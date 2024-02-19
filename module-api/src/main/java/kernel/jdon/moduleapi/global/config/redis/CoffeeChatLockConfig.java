package kernel.jdon.moduleapi.global.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "redis.lock.coffee-chat")
public class CoffeeChatLockConfig {
	private final Long waitTime;
	private final Long leaseTime;

}
