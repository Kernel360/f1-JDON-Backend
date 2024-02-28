package kernel.jdon.moduledomain.coffeechatmember.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.moduledomain.coffeechatmember.domain.CoffeeChatMember;

public interface CoffeeChatMemberDomainRepository extends JpaRepository<CoffeeChatMember, Long> {
}
