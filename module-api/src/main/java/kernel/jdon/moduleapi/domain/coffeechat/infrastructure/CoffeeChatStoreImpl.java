package kernel.jdon.moduleapi.domain.coffeechat.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatStore;
import kernel.jdon.moduledomain.coffeechat.domain.CoffeeChat;
import kernel.jdon.moduledomain.coffeechatmember.domain.CoffeeChatMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoffeeChatStoreImpl implements CoffeeChatStore {

    private final CoffeeChatRepository coffeeChatRepository;

    @Override
    public CoffeeChat save(CoffeeChat coffeeChat) {
        return coffeeChatRepository.save(coffeeChat);
    }

    @Override
    public void update(CoffeeChat findCoffeeChat, CoffeeChat updateCoffeeChat) {
        findCoffeeChat.updateCoffeeChat(updateCoffeeChat);
    }

    @Override
    public void deleteById(Long coffeeChatId) {
        coffeeChatRepository.deleteById(coffeeChatId);
    }

    @Override
    public void cancel(CoffeeChat findCoffeeChat, CoffeeChatMember findCoffeeChatMember) {
        findCoffeeChat.removeCoffeeChatMember(findCoffeeChatMember);
    }
}
