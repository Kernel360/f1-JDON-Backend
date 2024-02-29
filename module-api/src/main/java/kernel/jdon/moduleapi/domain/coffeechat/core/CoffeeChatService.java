package kernel.jdon.moduleapi.domain.coffeechat.core;

import org.springframework.data.domain.Pageable;

import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;

public interface CoffeeChatService {

    CoffeeChatInfo.FindCoffeeChatListResponse getCoffeeChatList(PageInfoRequest pageInfoRequest,
        CoffeeChatCommand.FindCoffeeChatListRequest command);

    CoffeeChatInfo.FindCoffeeChatResponse getCoffeeChat(Long coffeeChatId, Long memberId);

    CoffeeChatInfo.CreatedCoffeeChatResponse createCoffeeChat(CoffeeChatCommand.CreateCoffeeChatRequest request,
        Long memberId);

    void increaseViewCount(Long coffeeChatId);

    CoffeeChatInfo.UpdatedCoffeeChatResponse modifyCoffeeChat(CoffeeChatCommand.UpdateCoffeeChatRequest request,
        Long coffeeChatId);

    Long deleteCoffeeChat(Long coffeeChatId);

    CustomPageResponse getGuestCoffeeChatList(Long memberId, Pageable pageable);

    CustomPageResponse getHostCoffeeChatList(Long memberId, Pageable pageable);

    CoffeeChatInfo.AppliedCoffeeChatResponse applyCoffeeChat(Long coffeeChatId, Long memberId);
}
