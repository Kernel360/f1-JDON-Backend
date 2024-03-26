package kernel.jdon.modulebatch.domain.coffeechat.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import kernel.jdon.moduledomain.coffeechat.repository.CoffeeChatDomainRepository;

public interface CoffeeChatRepository extends CoffeeChatDomainRepository {
    @Modifying
    @Query("update CoffeeChat c set c.coffeeChatStatus = 'CLOSE' where c.meetDate < CURRENT_TIMESTAMP")
    void updateStatusToCloseForPastCoffeeChats();
}
