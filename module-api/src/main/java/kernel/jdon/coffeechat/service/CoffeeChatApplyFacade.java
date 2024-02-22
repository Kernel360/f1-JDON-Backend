package kernel.jdon.coffeechat.service;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import kernel.jdon.coffeechat.dto.response.ApplyCoffeeChatResponse;
import kernel.jdon.moduleapi.domain.coffeechat.error.CoffeeChatErrorCode;
import kernel.jdon.moduleapi.global.config.redis.CoffeeChatLockConfig;
import kernel.jdon.moduleapi.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoffeeChatApplyFacade {

	private final CoffeeChatService coffeeChatService;
	private final RedissonClient redissonClient;
	private final CoffeeChatLockConfig lockConfig;

	public ApplyCoffeeChatResponse apply(final Long coffeeChatId, final Long memberId) {
		RLock lock = redissonClient.getLock(String.format("apply:coffeeChat:%d", coffeeChatId));
		try {
			boolean available = lock.tryLock(lockConfig.getWaitTime(), lockConfig.getLeaseTime(),
				TimeUnit.SECONDS);
			if (!available) {
				log.info("커피챗 신청 중 lock 획득 실패, coffeeChatId={} memberId={}", coffeeChatId, memberId);
				throw new ApiException(CoffeeChatErrorCode.LOCK_ACQUISITION_FAILURE);
			}

			return coffeeChatService.apply(coffeeChatId, memberId);

		} catch (InterruptedException e) {
			log.info("커피챗 신청 lock 획득 중 thread interrupted, coffeeChatId={} memberId={}", coffeeChatId, memberId);
			throw new ApiException(CoffeeChatErrorCode.THREAD_INTERRUPTED, e);
		} finally {
			lock.unlock();
		}
	}
}
