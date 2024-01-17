package kernel.jdon.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.member.domain.Member;

public interface MemberDomainRepository extends JpaRepository<Member, Long> {
}
