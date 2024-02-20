package kernel.jdon.moduleapi.domain.favorite.application;

import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.favorite.core.FavoriteService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteFacade {
	private final FavoriteService favoriteService;
}
