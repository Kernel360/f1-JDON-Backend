package kernel.jdon.coffeechat.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.coffeechat.domain.CoffeeChatActiveStatus;

public interface CoffeeChatRepository extends CoffeeChatDomainRepository {

	Optional<CoffeeChat> findByIdAndIsDeletedFalse(Long id);

	Optional<CoffeeChat> findByIdAndCoffeeChatStatus(Long id, CoffeeChatActiveStatus status);

	Page<CoffeeChat> findAllByMemberIdAndIsDeletedFalse(Long id, Pageable pageable);
}
