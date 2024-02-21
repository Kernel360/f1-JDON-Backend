package kernel.jdon.member.repository;

import org.springframework.stereotype.Repository;

import kernel.jdon.member.domain.Member;

@Repository("legacyMemberRepository")
public interface MemberRepository extends MemberDomainRepository {
	Member findByEmail(String email);

	boolean existsByNickname(String nickname);
}
