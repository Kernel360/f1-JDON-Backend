package kernel.jdon.moduleapi.domain.favorite.core;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;

public interface FavoriteService {
	FavoriteInfo.UpdateResponse create(Long memberId, Long lectureId);

	FavoriteInfo.UpdateResponse remove(Long memberId, Long lectureId);

	FavoriteInfo.FindFavoriteListResponse getFavoriteList(Long memberId, PageInfoRequest pageInfoRequest);
}
