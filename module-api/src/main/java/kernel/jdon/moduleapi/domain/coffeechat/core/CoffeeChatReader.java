package kernel.jdon.moduleapi.domain.coffeechat.core;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;

public interface CoffeeChatReader {
	CoffeeChatInfo.FindCoffeeChatListResponse findCoffeeChatList(PageInfoRequest pageInfoRequest,
		CoffeeChatCommand.FindCoffeeChatListRequest command);

	CoffeeChat findExistCoffeeChat(Long coffeeChatId);
}
