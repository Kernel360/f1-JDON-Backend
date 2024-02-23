package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.moduleapi.domain.favorite.core.FavoriteStore;
import kernel.jdon.moduledomain.favorite.domain.Favorite;
import lombok.RequiredArgsConstructor;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteStoreImpl implements FavoriteStore {
	private final FavoriteRepository favoriteRepository;

	@Override
	@Transactional
	public Favorite save(Favorite favorite) {
		return favoriteRepository.save(favorite);
	}

	@Override
	@Transactional
	public void delete(Favorite favorite) {
		favoriteRepository.delete(favorite);
	}

}
