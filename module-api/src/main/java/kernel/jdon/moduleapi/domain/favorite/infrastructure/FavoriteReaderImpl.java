package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.favorite.application.FavoriteReader;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfo;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FavoriteReaderImpl implements FavoriteReader {
	private final FavoriteRepository favoriteRepository;

	@Override
	public CustomPageResponse<FavoriteInfo.FindResponse> findList(Long memberId, Pageable pageable) {
		Page<FavoriteInfo.FindResponse> page = favoriteRepository.findFavoriteByMemberId(memberId, pageable);
		return CustomPageResponse.of(page);
	}
}

