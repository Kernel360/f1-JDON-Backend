package kernel.jdon.moduleapi.domain.coffeechat.core;

import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;

public interface CoffeeChatService {

    CoffeeChatInfo.FindCoffeeChatListResponse getCoffeeChatList(PageInfoRequest pageInfoRequest,
        CoffeeChatCommand.FindCoffeeChatListRequest command);

    CoffeeChatInfo.FindCoffeeChatResponse getCoffeeChat(Long coffeeChatId, Long memberId);

    CoffeeChatInfo.CreateCoffeeChatResponse createCoffeeChat(CoffeeChatCommand.CreateCoffeeChatRequest request,
        Long memberId);

    void increaseViewCount(Long coffeeChatId);

    CoffeeChatInfo.UpdateCoffeeChatResponse modifyCoffeeChat(CoffeeChatCommand.UpdateCoffeeChatRequest request,
        Long coffeeChatId);

    CoffeeChatInfo.DeleteCoffeeChatResponse deleteCoffeeChat(Long coffeeChatId);

    CustomPageResponse getGuestCoffeeChatList(Long memberId, PageInfoRequest pageInfoRequest);

    CustomPageResponse getHostCoffeeChatList(Long memberId, PageInfoRequest pageInfoRequest);

    CoffeeChatInfo.ApplyCoffeeChatResponse applyCoffeeChat(Long coffeeChatId, Long memberId);

    CoffeeChatInfo.CancelCoffeeChatResponse cancelCoffeeChat(Long coffeeChatId, Long memberId);
}
