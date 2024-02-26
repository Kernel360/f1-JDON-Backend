package kernel.jdon.moduleapi.domain.coffeechat.core;

import kernel.jdon.coffeechat.domain.CoffeeChat;

public interface CoffeeChatStore {

    CoffeeChat save(CoffeeChat coffeeChat);

    void delete(Long id);
}
