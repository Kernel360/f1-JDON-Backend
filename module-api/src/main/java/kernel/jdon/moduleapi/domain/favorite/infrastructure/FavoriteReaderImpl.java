package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfo;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteReader;
import kernel.jdon.moduleapi.domain.favorite.presentation.FavoriteDtoMapper;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FavoriteReaderImpl implements FavoriteReader {
	private final FavoriteRepository favoriteRepository;
	private final FavoriteDtoMapper favoriteDtoMapper;

	@Override
	public CustomPageResponse<FavoriteInfo.FindResponse> findList(Long memberId, Pageable pageable) {
		Page<Favorite> favoritePage = favoriteRepository.findFavoriteByMemberId(memberId, pageable);
		Page<FavoriteInfo.FindResponse> infoPage = favoritePage.map(favoriteDtoMapper::of);

		return CustomPageResponse.of(infoPage);
	}
}

