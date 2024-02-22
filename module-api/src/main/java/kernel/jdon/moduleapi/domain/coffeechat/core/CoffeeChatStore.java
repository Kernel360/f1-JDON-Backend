package kernel.jdon.moduleapi.domain.coffeechat.core;

import kernel.jdon.coffeechat.domain.CoffeeChat;

public interface CoffeeChatStore {

    CoffeeChat store(CoffeeChat coffeeChat);

    void delete(Long id);
}
