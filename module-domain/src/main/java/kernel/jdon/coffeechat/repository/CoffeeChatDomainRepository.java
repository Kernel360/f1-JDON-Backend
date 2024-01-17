package kernel.jdon.coffeechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.coffeechat.domain.CoffeeChat;

public interface CoffeeChatDomainRepository extends JpaRepository<CoffeeChat, Long> {
}
