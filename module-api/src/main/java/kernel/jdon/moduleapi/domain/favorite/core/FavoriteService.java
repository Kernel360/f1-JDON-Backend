package kernel.jdon.moduleapi.domain.favorite.core;

import org.springframework.data.domain.Pageable;

public interface FavoriteService {
	FavoriteInfo.UpdateResponse create(Long memberId, FavoriteCommand.UpdateRequest request);

	void delete(Long memberId, Long lectureId);

	FavoriteInfo.FindPageResponse getList(final Long memberId, final Pageable pageable);
}
