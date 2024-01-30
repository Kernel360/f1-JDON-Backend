package kernel.jdon.coffeechat.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.coffeechat.domain.CoffeeChatActiveStatus;

public interface CoffeeChatRepository extends CoffeeChatDomainRepository {

	Optional<CoffeeChat> findByIdAndIsDeletedFalse(Long id);

	Optional<CoffeeChat> findByIdAndCoffeeChatStatus(Long id, CoffeeChatActiveStatus status);

	Page<CoffeeChat> findAllByMemberIdAndIsDeletedFalse(Long id, Pageable pageable);

	@Query("SELECT cc FROM CoffeeChat cc JOIN FETCH cc.member m JOIN FETCH m.jobCategory JOIN FETCH cc.coffeeChatMemberList ccm WHERE ccm.member.id = :memberId")
	Page<CoffeeChat> findAllByMemberId(@Param("memberId") Long memberId, Pageable pageable);
}
