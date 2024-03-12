package kernel.jdon.moduleapi.domain.member.infrastructure;

import kernel.jdon.moduledomain.member.domain.Member;
import kernel.jdon.moduledomain.member.repository.MemberDomainRepository;

public interface MemberRepository extends MemberDomainRepository {
    Member findByEmail(String email);

    boolean existsByNickname(String nickname);
}
