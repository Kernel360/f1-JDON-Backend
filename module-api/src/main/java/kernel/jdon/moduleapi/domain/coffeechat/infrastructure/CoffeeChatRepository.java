package kernel.jdon.moduleapi.domain.coffeechat.infrastructure;

import java.util.Optional;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.coffeechat.repository.CoffeeChatDomainRepository;

public interface CoffeeChatRepository extends CoffeeChatDomainRepository {

	Optional<CoffeeChat> findByIdAndIsDeletedFalse(Long id);
}
