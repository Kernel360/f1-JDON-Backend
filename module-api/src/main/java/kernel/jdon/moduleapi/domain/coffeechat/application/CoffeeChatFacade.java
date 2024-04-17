package kernel.jdon.moduleapi.domain.coffeechat.application;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatCommand;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatService;
import kernel.jdon.moduleapi.domain.coffeechat.error.CoffeeChatErrorCode;
import kernel.jdon.moduleapi.global.config.redis.CoffeeChatLockConfig;
import kernel.jdon.moduleapi.global.exception.ApiException;
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

    public CoffeeChatInfo.CreateCoffeeChatResponse createCoffeeChat(CoffeeChatCommand.CreateCoffeeChatRequest request,
        Long memberId) {
        return coffeeChatService.createCoffeeChat(request, memberId);
    }

    public CoffeeChatInfo.UpdateCoffeeChatResponse modifyCoffeeChat(CoffeeChatCommand.UpdateCoffeeChatRequest request) {
        return coffeeChatService.modifyCoffeeChat(request);
    }

    public CoffeeChatInfo.DeleteCoffeeChatResponse deleteCoffeeChat(Long coffeeChatId, Long memberId) {
        return coffeeChatService.deleteCoffeeChat(coffeeChatId, memberId);
    }

    public CoffeeChatInfo.FindCoffeeChatListResponse getGuestCoffeeChatList(Long memberId,
        PageInfoRequest pageInfoRequest) {
        return coffeeChatService.getGuestCoffeeChatList(memberId, pageInfoRequest);
    }

    public CoffeeChatInfo.FindCoffeeChatListResponse getHostCoffeeChatList(Long memberId,
        PageInfoRequest pageInfoRequest) {
        return coffeeChatService.getHostCoffeeChatList(memberId, pageInfoRequest);
    }

    public CoffeeChatInfo.ApplyCoffeeChatResponse applyCoffeeChat(Long coffeeChatId, Long memberId) {
        boolean isLocked = false;
        RLock lock = redissonClient.getLock(String.format("apply:coffeeChat:%d", coffeeChatId));
        try {
            isLocked = lock.tryLock(lockConfig.getWaitTime(), lockConfig.getLeaseTime(),
                TimeUnit.SECONDS);
            if (!isLocked) {
                log.info("커피챗 신청 lock 획득 실패, coffeeChatId={}, memberId={}", coffeeChatId, memberId);
                throw new ApiException(CoffeeChatErrorCode.LOCK_ACQUISITION_FAILURE);
            }
            log.info("커피챗 신청 lock 획득 성공, coffeeChatId={}, memberId={}", coffeeChatId, memberId);

            return coffeeChatService.applyCoffeeChat(coffeeChatId, memberId);

        } catch (InterruptedException e) {
            log.info("커피챗 신청 lock 획득 중 thread interrupted, coffeeChatId={} memberId={}", coffeeChatId, memberId);
            throw new ApiException(CoffeeChatErrorCode.THREAD_INTERRUPTED);
        } finally {
            if (isLocked) {
                lock.unlock();
            }
            log.info("커피챗 신청 lock 반납 성공, coffeeChatId={}, memberId={}", coffeeChatId, memberId);
        }
    }

    public CoffeeChatInfo.CancelCoffeeChatResponse cancelCoffeeChatApplication(Long coffeeChatId, Long memberId) {
        return coffeeChatService.cancelCoffeeChat(coffeeChatId, memberId);
    }
}
