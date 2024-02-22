package kernel.jdon.moduleapi.domain.coffeechat.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoffeeChatStoreImpl implements CoffeeChatStore {

    private final CoffeeChatRepository coffeeChatRepository;

    @Override
    public CoffeeChat store(CoffeeChat coffeeChat) {
        return coffeeChatRepository.save(coffeeChat);
    }

    @Override
    public void delete(Long coffeeChatid) {
        coffeeChatRepository.deleteById(coffeeChatid);
    }
}
