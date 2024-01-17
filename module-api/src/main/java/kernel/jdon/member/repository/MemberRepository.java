package kernel.jdon.member.repository;

import kernel.jdon.member.domain.Member;

public interface MemberRepository extends MemberDomainRepository {
	Member findByEmail(String email);
	boolean existsByNickname(String nickname);
}
