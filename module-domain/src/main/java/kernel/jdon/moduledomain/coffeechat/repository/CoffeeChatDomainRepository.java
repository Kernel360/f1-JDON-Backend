package kernel.jdon.moduledomain.coffeechat.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.moduledomain.coffeechat.domain.CoffeeChat;

public interface CoffeeChatDomainRepository extends JpaRepository<CoffeeChat, Long> {
}
