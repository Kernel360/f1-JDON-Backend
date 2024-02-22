package kernel.jdon.moduleapi.domain.favorite.core.memberFavorite;

import kernel.jdon.member.domain.Member;

public interface MemberFavoriteReader {
	Member findById(Long memberId);

	boolean existsById(Long memberId);
}
