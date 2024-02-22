package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteReader;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FavoriteReaderImpl implements FavoriteReader {
	private final FavoriteRepository favoriteRepository;

	@Override
	public Page<Favorite> findList(Long memberId, Pageable pageable) {
		Page<Favorite> favoritePage = favoriteRepository.findFavoriteByMemberId(memberId, pageable);

		return favoritePage;
	}
}

