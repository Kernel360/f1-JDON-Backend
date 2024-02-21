package kernel.jdon.moduleapi.domain.favorite.core;

import org.springframework.data.domain.Pageable;

public interface FavoriteService {
	FavoriteInfo.UpdateResponse update(final Long memberId, final FavoriteCommand.UpdateRequest command);

	FavoriteInfo.FindPageResponse getList(final Long memberId, final Pageable pageable);
}
