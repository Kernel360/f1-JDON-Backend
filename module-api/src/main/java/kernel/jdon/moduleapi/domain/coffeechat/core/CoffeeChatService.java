package kernel.jdon.moduleapi.domain.coffeechat.core;

import org.springframework.data.domain.Pageable;

import kernel.jdon.moduleapi.global.page.CustomPageResponse;

public interface CoffeeChatService {

	CustomPageResponse<CoffeeChatInfo.FindCoffeeChatListResponse> getCoffeeChatList(Pageable pageable,
		CoffeeChatCommand.FindCoffeeChatListRequest command);

	CoffeeChatInfo.FindResponse getCoffeeChat(Long coffeeChatId);

	void increaseViewCount(Long coffeeChatId);
}
