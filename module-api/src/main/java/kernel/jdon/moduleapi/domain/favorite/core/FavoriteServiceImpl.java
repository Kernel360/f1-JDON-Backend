package kernel.jdon.moduleapi.domain.favorite.core;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
	private final FavoriteReader favoriteReader;
	private final FavoriteStore favoriteStore;
	
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
		final CustomPageResponse<FavoriteInfo.FindResponse> favoritePage = favoriteReader.findList(memberId, pageable);

		return new FavoriteInfo.FindPageResponse(favoritePage);
	}
}
