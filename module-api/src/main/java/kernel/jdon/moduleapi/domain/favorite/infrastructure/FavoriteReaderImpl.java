package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfo;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfoMapper;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteReader;
import kernel.jdon.moduleapi.global.page.CustomJpaPageInfo;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.favorite.domain.Favorite;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FavoriteReaderImpl implements FavoriteReader {
	private final FavoriteRepository favoriteRepository;
	private final FavoriteInfoMapper favoriteInfoMapper;

	@Override
	public FavoriteInfo.FindFavoriteListResponse findList(final Long memberId, final PageInfoRequest pageInfoRequest) {
		Pageable pageable = PageRequest.of(pageInfoRequest.getPage(), pageInfoRequest.getSize());

		final Page<Favorite> favoritePage = favoriteRepository.findFavoriteByMemberId(memberId, pageable);
		final Page<FavoriteReaderInfo.FindFavoriteListResponse> favoritesPage = favoritePage.map(
			favoriteInfoMapper::of);

		final List<FavoriteInfo.FindFavorite> list = favoritesPage.getContent().stream()
			.map(FavoriteInfo.FindFavorite::of)
			.toList();

		return new FavoriteInfo.FindFavoriteListResponse(list, new CustomJpaPageInfo(favoritesPage));
	}

	@Override
	public Optional<Favorite> findFavoriteByMemberIdAndInflearnCourseId(final Long memberId, final Long lectureId) {
		return favoriteRepository.findFavoriteByMemberIdAndInflearnCourseId(memberId, lectureId);
	}

	@Override
	public Optional<Favorite> findById(final Long favoriteId) {
		return favoriteRepository.findById(favoriteId);
	}

	@Override
	public Favorite save(final Favorite favorite) {
		return favoriteRepository.save(favorite);
	}
}

