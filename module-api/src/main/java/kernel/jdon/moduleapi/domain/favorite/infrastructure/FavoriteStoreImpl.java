package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.favorite.core.FavoriteStore;
import kernel.jdon.moduledomain.favorite.domain.Favorite;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FavoriteStoreImpl implements FavoriteStore {
	private final FavoriteRepository favoriteRepository;

	@Override
	public Favorite save(final Favorite favorite) {
		return favoriteRepository.save(favorite);
	}

	@Override
	public void delete(final Favorite favorite) {
		favoriteRepository.delete(favorite);
	}

}
