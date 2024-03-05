package kernel.jdon.moduleapi.domain.coffeechat.core;

import kernel.jdon.moduledomain.coffeechat.domain.CoffeeChat;
import kernel.jdon.moduledomain.coffeechatmember.domain.CoffeeChatMember;

public interface CoffeeChatStore {

    CoffeeChat save(CoffeeChat coffeeChat);

    void update(CoffeeChat findCoffeeChat, CoffeeChat updateCoffeeChat);

    void deleteById(Long id);

    void delete(CoffeeChatMember coffeeChatMember);
}
