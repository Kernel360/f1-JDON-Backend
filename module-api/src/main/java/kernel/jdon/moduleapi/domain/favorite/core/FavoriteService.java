package kernel.jdon.moduleapi.domain.favorite.core;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;

public interface FavoriteService {
	FavoriteInfo.UpdateResponse save(Long memberId, Long lectureId);

	FavoriteInfo.UpdateResponse delete(Long memberId, Long lectureId);
	
	FavoriteInfo.FindFavoriteListResponse getList(Long memberId, PageInfoRequest pageInfoRequest);
}
