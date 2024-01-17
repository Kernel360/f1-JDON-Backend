package kernel.jdon.coffeechat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.coffeechat.domain.CoffeeChat;

public interface CoffeeChatRepository extends CoffeeChatDomainRepository {

	Optional<CoffeeChat> findByIdAndIsDeletedFalse(Long id);

}
