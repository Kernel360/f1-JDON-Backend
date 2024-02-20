package kernel.jdon.moduleapi.domain.favorite.core;

import java.awt.print.Pageable;

public interface FavoriteService {
	FavoriteInfo.UpdateResponse update(final Long memberId, final FavoriteCommand.UpdateRequest command);

	FavoriteInfo.FindResponse getList(final Long memberId, final Pageable pageable);
}
