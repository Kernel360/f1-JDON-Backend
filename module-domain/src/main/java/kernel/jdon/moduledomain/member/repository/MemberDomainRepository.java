package kernel.jdon.moduledomain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.moduledomain.member.domain.Member;

public interface MemberDomainRepository extends JpaRepository<Member, Long> {
}
