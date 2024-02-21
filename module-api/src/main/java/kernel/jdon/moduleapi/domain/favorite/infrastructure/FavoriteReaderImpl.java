package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel.jdon.favorite.domain.Favorite;
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
		Page<Favorite> favoritePage = favoriteRepository.findFavoriteByMemberId(memberId, pageable);
		Page<FavoriteInfo.FindResponse> infoPage = favoritePage.map(this::convertToFavoriteInfo);

		return CustomPageResponse.of(infoPage);
	}

	private FavoriteInfo.FindResponse convertToFavoriteInfo(Favorite favorite) {
		return new FavoriteInfo.FindResponse(
			favorite.getInflearnCourse().getId(),
			favorite.getInflearnCourse().getTitle(),
			favorite.getInflearnCourse().getLectureUrl(),
			favorite.getInflearnCourse().getImageUrl(),
			favorite.getInflearnCourse().getInstructor(),
			favorite.getInflearnCourse().getStudentCount(),
			favorite.getInflearnCourse().getPrice()
		);
	}
}

