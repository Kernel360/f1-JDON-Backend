package kernel.jdon.moduleapi.domain.member.core;

import kernel.jdon.member.domain.Member;

public interface MemberReader {
	Member findById(Long memberId);

	boolean existsById(Long memberId);
}
