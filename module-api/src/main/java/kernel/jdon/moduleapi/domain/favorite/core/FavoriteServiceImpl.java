package kernel.jdon.moduleapi.domain.favorite.core;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.moduleapi.domain.favorite.application.FavoriteReader;
import kernel.jdon.moduleapi.domain.favorite.application.FavoriteStore;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
	private final FavoriteReader favoriteReader;
	private final FavoriteStore favoriteStore;

	@Override
	public FavoriteInfo.UpdateResponse update(Long memberId, FavoriteCommand.UpdateRequest command) {
		return null;
	}

	@Override
	public FavoriteInfo.FindPageResponse getList(Long memberId, Pageable pageable) {
		CustomPageResponse<FavoriteInfo.FindResponse> favoritePage = favoriteReader.findList(memberId, pageable);

		return new FavoriteInfo.FindPageResponse(favoritePage);
	}
}
