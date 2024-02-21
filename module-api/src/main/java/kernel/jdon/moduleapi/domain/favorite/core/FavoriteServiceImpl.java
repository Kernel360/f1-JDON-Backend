package kernel.jdon.moduleapi.domain.favorite.core;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.favorite.application.FavoriteReader;
import kernel.jdon.moduleapi.domain.favorite.application.FavoriteStore;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
	private final FavoriteReader favoriteReader;
	private final FavoriteStore favoriteStore;

	@Override
	public FavoriteInfo.UpdateResponse update(Long memberId, FavoriteCommand.UpdateRequest command) {
		final FavoriteInfo.UpdateResponse updateFavorite = favoriteStore.update(memberId, command);

		return new FavoriteInfo.UpdateResponse(updateFavorite.getLectureId());
	}

	@Override
	public FavoriteInfo.FindPageResponse getList(Long memberId, Pageable pageable) {
		final CustomPageResponse<FavoriteInfo.FindResponse> favoritePage = favoriteReader.findList(memberId, pageable);

		return new FavoriteInfo.FindPageResponse(favoritePage);
	}
}
