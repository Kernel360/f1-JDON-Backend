package kernel.jdon.moduleapi.domain.favorite.core;

public interface FavoriteService {
	FavoriteInfo.UpdateResponse update(final FavoriteCommand.UpdateRequest command);

	FavoriteInfo.FindResponse findList();
}
