package kernel.jdon.moduleapi.domain.favorite.core;

import org.springframework.data.domain.Pageable;

public interface FavoriteService {
	FavoriteInfo.UpdateResponse save(Long memberId, Long lectureId);

	FavoriteInfo.UpdateResponse delete(Long memberId, Long lectureId);

	FavoriteInfo.FindPageResponse getList(final Long memberId, final Pageable pageable);
}
