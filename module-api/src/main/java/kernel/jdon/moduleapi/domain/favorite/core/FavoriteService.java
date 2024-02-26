package kernel.jdon.moduleapi.domain.favorite.core;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;

public interface FavoriteService {
	FavoriteInfo.UpdateResponse save(Long memberId, Long lectureId);

	FavoriteInfo.UpdateResponse delete(Long memberId, Long lectureId);

	// FavoriteInfo.FindPageResponse getList(final Long memberId, final Pageable pageable);

	FavoriteInfo.FindFavoriteListResponse getList(Long memberId, PageInfoRequest pageInfoRequest);
}
