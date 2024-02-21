package kernel.jdon.moduleapi.domain.member.infrastructure;

import kernel.jdon.member.domain.Member;
import kernel.jdon.member.repository.MemberDomainRepository;

public interface MemberRepository extends MemberDomainRepository {
	Member findByEmail(String email);

	boolean existsByNickname(String nickname);
}
