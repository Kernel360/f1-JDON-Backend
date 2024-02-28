package kernel.jdon.moduleapi.domain.coffeechat.core;

import kernel.jdon.moduledomain.coffeechat.domain.CoffeeChat;

public interface CoffeeChatStore {

    CoffeeChat save(CoffeeChat coffeeChat);

    void update(CoffeeChat findCoffeeChat, CoffeeChat updateCoffeeChat);

    void deleteById(Long id);
}
