package kernel.jdon.coffeechatmember.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.coffeechatmember.domain.CoffeeChatMember;

public interface CoffeeChatMemberDomainRepository extends JpaRepository<CoffeeChatMember, Long> {
}
