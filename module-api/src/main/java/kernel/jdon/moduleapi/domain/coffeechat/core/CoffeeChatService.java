package kernel.jdon.moduleapi.domain.coffeechat.core;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;

public interface CoffeeChatService {

	CoffeeChatInfo.FindCoffeeChatListResponse getCoffeeChatList(PageInfoRequest pageInfoRequest,
		CoffeeChatCommand.FindCoffeeChatListRequest command);

	CoffeeChatInfo.FindResponse getCoffeeChat(Long coffeeChatId);

	void increaseViewCount(Long coffeeChatId);
}
