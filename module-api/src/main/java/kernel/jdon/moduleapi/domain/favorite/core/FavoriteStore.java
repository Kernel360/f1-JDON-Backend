package kernel.jdon.moduleapi.domain.favorite.core;

public interface FavoriteStore {
	FavoriteInfo.UpdateResponse update(final Long memberId, final FavoriteCommand.UpdateRequest command);
}
