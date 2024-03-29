package kernel.jdon.moduleapi.domain.coffeechat.infrastructure;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import kernel.jdon.moduledomain.coffeechatmember.domain.CoffeeChatMember;
import kernel.jdon.moduledomain.coffeechatmember.repository.CoffeeChatMemberDomainRepository;

public interface CoffeeChatMemberRepository extends CoffeeChatMemberDomainRepository {
    @Query("select ccm from CoffeeChatMember ccm join fetch ccm.coffeeChat cc join fetch cc.member m join fetch m.jobCategory where ccm.member.id = :memberId")
    Page<CoffeeChatMember> findAllByMemberId(Long memberId, Pageable pageable);

    boolean existsByCoffeeChatIdAndMemberId(Long coffeeChatId, Long memberId);

    Optional<CoffeeChatMember> findByCoffeeChatIdAndMemberId(Long coffeeChatId, Long memberId);
}
