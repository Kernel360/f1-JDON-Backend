package kernel.jdon.coffeechat.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel.jdon.coffeechat.domain.CoffeeChat;

public interface CoffeeChatRepository extends CoffeeChatDomainRepository, CustomCoffeeChatRepository {

	Optional<CoffeeChat> findByIdAndIsDeletedFalse(Long id);

	Page<CoffeeChat> findAllByMemberIdAndIsDeletedFalse(Long id, Pageable pageable);

}
