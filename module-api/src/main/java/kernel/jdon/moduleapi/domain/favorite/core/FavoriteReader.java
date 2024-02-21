package kernel.jdon.moduleapi.domain.favorite.core;

import org.springframework.data.domain.Pageable;

import kernel.jdon.moduleapi.global.page.CustomPageResponse;

public interface FavoriteReader {
	CustomPageResponse<FavoriteInfo.FindResponse> findList(final Long memberId, Pageable pageable);

}
