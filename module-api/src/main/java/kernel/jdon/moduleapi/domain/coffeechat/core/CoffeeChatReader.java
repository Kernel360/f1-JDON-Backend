package kernel.jdon.moduleapi.domain.coffeechat.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel.jdon.coffeechat.domain.CoffeeChat;

public interface CoffeeChatReader {
	Page<CoffeeChatInfo.FindCoffeeChatListResponse> findCoffeeChatList(Pageable pageable,
		CoffeeChatCommand.FindCoffeeChatListRequest command);

	CoffeeChat findExistCoffeeChat(Long coffeeChatId);
}
