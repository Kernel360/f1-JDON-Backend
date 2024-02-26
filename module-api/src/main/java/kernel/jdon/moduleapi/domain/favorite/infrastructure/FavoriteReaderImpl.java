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
import kernel.jdon.moduleapi.global.page.CustomPageInfo;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.favorite.domain.Favorite;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FavoriteReaderImpl implements FavoriteReader {
	private final FavoriteRepository favoriteRepository;
	private final FavoriteInfoMapper favoriteInfoMapper;

	@Override
	public FavoriteInfo.FindFavoriteListResponse findList(Long memberId, PageInfoRequest pageInfoRequest) {
		Pageable pageable = PageRequest.of(pageInfoRequest.getPage(), pageInfoRequest.getSize());

		Page<Favorite> favoritePage = favoriteRepository.findFavoriteByMemberId(memberId, pageable);
		Page<FavoriteReaderInfo.FindFavoriteListResponse> favoritesPage = favoritePage.map(favoriteInfoMapper::of);

		List<FavoriteInfo.FindFavoriteInfo> list = favoritesPage.getContent().stream()
			.map(FavoriteInfo.FindFavoriteInfo::of)
			.toList();

		CustomPageInfo customPageInfo = new CustomPageInfo(
			favoritesPage.getNumber(),
			favoritesPage.getTotalElements(),
			favoritesPage.getSize(),
			favoritesPage.isFirst(),
			favoritesPage.isLast(),
			favoritesPage.isEmpty()
		);

		return new FavoriteInfo.FindFavoriteListResponse(list, customPageInfo);
	}

	@Override
	public Optional<Favorite> findFavoriteByMemberIdAndInflearnCourseId(Long memberId, Long lectureId) {
		return favoriteRepository.findFavoriteByMemberIdAndInflearnCourseId(memberId, lectureId);
	}

	@Override
	public Optional<Favorite> findById(Long favoriteId) {
		return favoriteRepository.findById(favoriteId);
	}

	@Override
	public Favorite save(Favorite favorite) {
		return favoriteRepository.save(favorite);
	}
}

