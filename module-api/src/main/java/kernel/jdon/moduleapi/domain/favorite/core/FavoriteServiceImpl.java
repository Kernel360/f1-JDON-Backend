package kernel.jdon.moduleapi.domain.favorite.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.moduleapi.domain.favorite.presentation.FavoriteDtoMapper;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
	private final FavoriteReader favoriteReader;
	private final FavoriteStore favoriteStore;
	private final FavoriteDtoMapper favoriteDtoMapper;

	@Override
	public FavoriteInfo.UpdateResponse create(Long memberId, FavoriteCommand.UpdateRequest command) {
		return favoriteStore.create(memberId, command);
	}

	@Override
	public void delete(Long memberId, Long lectureId) {
		favoriteStore.delete(memberId, lectureId);
	}

	@Override
	public FavoriteInfo.FindPageResponse getList(Long memberId, Pageable pageable) {
		Page<Favorite> favoritePage = favoriteReader.findList(memberId, pageable);
		Page<FavoriteInfo.FindResponse> infoPage = favoritePage.map(favoriteDtoMapper::of);

		return new FavoriteInfo.FindPageResponse(CustomPageResponse.of(infoPage));
	}
}
