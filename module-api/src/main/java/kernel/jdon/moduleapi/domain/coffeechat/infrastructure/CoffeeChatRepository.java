package kernel.jdon.moduleapi.domain.coffeechat.infrastructure;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel.jdon.moduledomain.coffeechat.domain.CoffeeChat;
import kernel.jdon.moduledomain.coffeechat.repository.CoffeeChatDomainRepository;

public interface CoffeeChatRepository extends CoffeeChatDomainRepository, CustomCoffeeChatRepository {

    Optional<CoffeeChat> findByIdAndIsDeletedFalse(Long id);

    Page<CoffeeChat> findAllByMemberIdAndIsDeletedFalse(Long id, Pageable pageable);

}
