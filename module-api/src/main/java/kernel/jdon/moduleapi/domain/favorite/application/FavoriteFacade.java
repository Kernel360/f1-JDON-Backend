package kernel.jdon.moduleapi.domain.favorite.application;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.favorite.core.FavoriteCommand;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfo;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteService;
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

	public FavoriteInfo.FindPageResponse getList(final Long memberId, Pageable pageable) {
		return favoriteService.getList(memberId, pageable);
	}
}
