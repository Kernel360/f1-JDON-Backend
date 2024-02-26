package kernel.jdon.moduleapi.domain.favorite.core;

import java.util.Optional;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.favorite.domain.Favorite;

public interface FavoriteReader {
	// Page<Favorite> findList(final Long memberId, Pageable pageable);
	FavoriteInfo.FindFavoriteListResponse findList(Long memberId, PageInfoRequest pageInfoRequest);

	Optional<Favorite> findFavoriteByMemberIdAndInflearnCourseId(Long memberId, Long lectureId);

	Optional<Favorite> findById(Long favoriteId);

	Favorite save(Favorite favorite);
}
