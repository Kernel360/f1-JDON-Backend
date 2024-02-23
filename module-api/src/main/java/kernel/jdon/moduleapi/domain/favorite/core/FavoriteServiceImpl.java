package kernel.jdon.moduleapi.domain.favorite.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.favorite.core.inflearnFavorite.InflearnFavoriteReader;
import kernel.jdon.moduleapi.domain.favorite.core.memberFavorite.MemberFavoriteReader;
import kernel.jdon.moduleapi.domain.favorite.presentation.FavoriteDtoMapper;
import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
	private final FavoriteReader favoriteReader;
	private final FavoriteStore favoriteStore;
	private final MemberFavoriteReader memberFavoriteReader;
	private final InflearnFavoriteReader inflearnFavoriteReader;
	private final FavoriteDtoMapper favoriteDtoMapper;

	@Override
	public FavoriteInfo.UpdateResponse create(Long memberId, Long lectureId) {
		Member findMember = memberFavoriteReader.findById(memberId);
		InflearnCourse findInflearnCourse = inflearnFavoriteReader.findById(lectureId);

		return favoriteStore.create(findMember, findInflearnCourse);
	}

	@Override
	public FavoriteInfo.UpdateResponse delete(Long memberId, Long lectureId) {
		boolean memberExists = memberFavoriteReader.existsById(memberId);
		if (!memberExists) {
			throw new ApiException(MemberErrorCode.NOT_FOUND_MEMBER);
		}
		return favoriteStore.delete(memberId, lectureId);
	}

	@Override
	public FavoriteInfo.FindPageResponse getList(Long memberId, Pageable pageable) {
		Page<Favorite> favoritePage = favoriteReader.findList(memberId, pageable);
		Page<FavoriteInfo.FindResponse> infoPage = favoritePage.map(favoriteDtoMapper::of);

		return new FavoriteInfo.FindPageResponse(CustomPageResponse.of(infoPage));
	}
}
