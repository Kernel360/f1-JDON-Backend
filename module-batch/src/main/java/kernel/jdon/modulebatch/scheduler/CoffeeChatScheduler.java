package kernel.jdon.modulebatch.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.modulebatch.domain.coffeechat.repository.CoffeeChatRepository;
import kernel.jdon.modulecommon.slack.SlackSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoffeeChatScheduler {
    private final CoffeeChatRepository coffeeChatRepository;
    private final SlackSender slackSender;

    @Transactional
    @Scheduled(cron = "0 0/30 * * * ?")
    public void expirePastCoffeeChats() {
        log.info("[expirePastCoffeeChats] 스케쥴러 실행");
        slackSender.sendSchedulerStart("expirePastCoffeeChats");
        try {
            coffeeChatRepository.updateStatusToCloseForPastCoffeeChats();
        } catch (Exception e) {
            log.error("[expirePastCoffeeChats] 스케쥴러 실행 중 Error 발생", e);
        }
        log.info("[expirePastCoffeeChats] 스케쥴러 종료");
        slackSender.sendSchedulerEnd("expirePastCoffeeChats");
    }
}
