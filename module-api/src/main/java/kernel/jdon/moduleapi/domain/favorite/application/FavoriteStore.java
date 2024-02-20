package kernel.jdon.moduleapi.domain.favorite.application;

import kernel.jdon.moduleapi.domain.favorite.core.FavoriteCommand;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfo;

public interface FavoriteStore {
	FavoriteInfo.UpdateResponse update(final Long memberId, final FavoriteCommand.UpdateRequest command);
}
