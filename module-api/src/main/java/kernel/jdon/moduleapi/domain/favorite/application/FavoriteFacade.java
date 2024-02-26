package kernel.jdon.moduleapi.domain.favorite.application;

import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.favorite.core.FavoriteCommand;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfo;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteService;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteFacade {
	private final FavoriteService favoriteService;

	public FavoriteInfo.UpdateResponse update(final Long memberId, FavoriteCommand.UpdateRequest request) {
		if (request.getIsFavorite()) {
			return favoriteService.save(memberId, request.getLectureId());
		}
		return favoriteService.delete(memberId, request.getLectureId());
	}

	public FavoriteInfo.FindFavoriteListResponse getList(
		final Long memberId,
		final PageInfoRequest pageInfoRequest) {

		return favoriteService.getList(memberId, pageInfoRequest);
	}
}
