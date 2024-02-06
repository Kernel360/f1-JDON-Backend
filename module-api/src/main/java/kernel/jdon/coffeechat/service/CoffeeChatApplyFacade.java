package kernel.jdon.coffeechat.service;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import kernel.jdon.coffeechat.error.CoffeeChatErrorCode;
import kernel.jdon.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CoffeeChatApplyFacade {

	private final CoffeeChatService coffeeChatService;
	private final RedissonClient redissonClient;

	public void apply(final Long coffeeChatId, final Long memberId) {
		RLock lock = redissonClient.getLock(String.format("apply:coffeeChat:%d", coffeeChatId));
		try {
			boolean available = lock.tryLock(3, 1, TimeUnit.SECONDS);
			if (!available) {
				log.info("커피챗 신청 중 lock 획득 실패, coffeeChatId={} memberId={}", coffeeChatId, memberId);
				throw new ApiException(CoffeeChatErrorCode.LOCK_ACQUISITION_FAILURE);
			}
			coffeeChatService.apply(coffeeChatId, memberId);
		} catch (InterruptedException e) {
			log.info("커피챗 신청 중 lock 획득 중 thread interrupted, coffeeChatId={} memberId={}", coffeeChatId, memberId);
			throw new ApiException(CoffeeChatErrorCode.THREAD_INTERRUPTED, e);
		} finally {
			lock.unlock();
		}
	}
}
