package kernel.jdon.modulebatch.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.modulebatch.domain.coffeechat.repository.CoffeeChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoffeeChatScheduler {
    private final CoffeeChatRepository coffeeChatRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    public void expirePastCoffeeChats() {
        coffeeChatRepository.updateStatusToCloseForPastCoffeeChats();
    }
}
