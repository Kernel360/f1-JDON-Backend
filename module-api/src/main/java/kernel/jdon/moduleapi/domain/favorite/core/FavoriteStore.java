package kernel.jdon.moduleapi.domain.favorite.core;

import kernel.jdon.moduledomain.favorite.domain.Favorite;

public interface FavoriteStore {
	Favorite save(Favorite favorite);

	Favorite delete(Favorite favorite);
}
