package kernel.jdon.moduleapi.domain.favorite.core;

import java.util.Optional;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.favorite.domain.Favorite;

public interface FavoriteReader {
	FavoriteInfo.FindFavoriteListResponse findList(Long memberId, PageInfoRequest pageInfoRequest);

	Optional<Favorite> findExistingFavoriteByMemberIdAndInflearnCourseId(Long memberId, Long lectureId);

	Favorite findFavoriteByMemberIdAndInflearnCourseId(Long memberId, Long lectureId);

	Favorite save(Favorite favorite);
}
