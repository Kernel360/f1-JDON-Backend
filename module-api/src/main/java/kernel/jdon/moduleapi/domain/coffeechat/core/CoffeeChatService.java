package kernel.jdon.moduleapi.domain.coffeechat.core;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;

public interface CoffeeChatService {

	CoffeeChatInfo.FindCoffeeChatListResponse getCoffeeChatList(PageInfoRequest pageInfoRequest,
		CoffeeChatCommand.FindCoffeeChatListRequest command);

    CoffeeChatInfo.FindResponse getCoffeeChat(Long coffeeChatId);

    Long createCoffeeChat(CoffeeChatCommand.CreateRequest request, Long memberId);

    void increaseViewCount(Long coffeeChatId);

    Long updateCoffeeChat(CoffeeChatCommand.UpdateRequest request, Long coffeeChatId);

    Long deleteCoffeeChat(Long coffeeChatId);
}
