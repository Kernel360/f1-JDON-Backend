package kernel.jdon.moduleapi.domain.coffeechat.core;

import kernel.jdon.coffeechat.domain.CoffeeChat;

public interface CoffeeChatReader {
	CoffeeChat findExistCoffeeChat(Long coffeeChatId);
}
