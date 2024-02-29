package kernel.jdon.moduleapi.domain.coffeechat.application;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatCommand;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatService;
import kernel.jdon.moduleapi.domain.coffeechat.error.CoffeeChatErrorCode;
import kernel.jdon.moduleapi.global.config.redis.CoffeeChatLockConfig;
import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CoffeeChatFacade {

    private final CoffeeChatService coffeeChatService;
    private final RedissonClient redissonClient;
    private final CoffeeChatLockConfig lockConfig;

    public CoffeeChatInfo.FindCoffeeChatListResponse getCoffeeChatList(
        final PageInfoRequest pageInfoRequest,
        final CoffeeChatCommand.FindCoffeeChatListRequest command) {
        return coffeeChatService.getCoffeeChatList(pageInfoRequest, command);
    }

    public CoffeeChatInfo.FindCoffeeChatResponse getCoffeeChat(Long coffeeChatId, Long memberId) {
        CoffeeChatInfo.FindCoffeeChatResponse findCoffeeChatResponse = coffeeChatService.getCoffeeChat(coffeeChatId,
            memberId);
        coffeeChatService.increaseViewCount(coffeeChatId);

        return findCoffeeChatResponse;
    }

    public CoffeeChatInfo.CreatedCoffeeChatResponse createCoffeeChat(CoffeeChatCommand.CreateCoffeeChatRequest request,
        Long memberId) {
        return coffeeChatService.createCoffeeChat(request, memberId);
    }

    public CoffeeChatInfo.UpdatedCoffeeChatResponse modifyCoffeeChat(CoffeeChatCommand.UpdateCoffeeChatRequest request,
        Long coffeeChatId) {
        return coffeeChatService.modifyCoffeeChat(request, coffeeChatId);
    }

    public CoffeeChatInfo.DeletedCoffeeChatResponse deleteCoffeeChat(Long coffeeChatId) {
        return coffeeChatService.deleteCoffeeChat(coffeeChatId);
    }

    public CustomPageResponse<CoffeeChatInfo.FindCoffeeChat> getGuestCoffeeChatList(Long memberId,
        Pageable pageable) {
        return coffeeChatService.getGuestCoffeeChatList(memberId, pageable);
    }

    public CustomPageResponse<CoffeeChatInfo.FindCoffeeChat> getHostCoffeeChatList(Long memberId, Pageable pageable) {
        return coffeeChatService.getHostCoffeeChatList(memberId, pageable);
    }

    public CoffeeChatInfo.AppliedCoffeeChatResponse applyCoffeeChat(Long coffeeChatId, Long memberId) {
        RLock lock = redissonClient.getLock(String.format("apply:coffeeChat:%d", coffeeChatId));
        try {
            boolean available = lock.tryLock(lockConfig.getWaitTime(), lockConfig.getLeaseTime(),
                TimeUnit.SECONDS);
            if (!available) {
                log.info("커피챗 신청 중 lock 획득 실패, coffeeChatId={} memberId={}", coffeeChatId, memberId);
                throw new ApiException(CoffeeChatErrorCode.LOCK_ACQUISITION_FAILURE);
            }

            return coffeeChatService.applyCoffeeChat(coffeeChatId, memberId);

        } catch (InterruptedException e) {
            log.info("커피챗 신청 lock 획득 중 thread interrupted, coffeeChatId={} memberId={}", coffeeChatId, memberId);
            throw new ApiException(CoffeeChatErrorCode.THREAD_INTERRUPTED);
        } finally {
            lock.unlock();
        }
    }
}
