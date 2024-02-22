package kernel.jdon.moduleapi.domain.coffeechat.core;

public interface CoffeeChatService {
	CoffeeChatInfo.FindResponse getCoffeeChat(Long coffeeChatId);

	void increaseViewCount(Long coffeeChatId);
}
