package kernel.jdon.moduleapi.domain.favorite.core;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.favorite.error.FavoriteErrorCode;
import kernel.jdon.moduleapi.domain.inflearncourse.core.InflearnReader;
import kernel.jdon.moduleapi.domain.member.core.MemberReader;
import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.favorite.domain.Favorite;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {
	private final FavoriteReader favoriteReader;
	private final FavoriteStore favoriteStore;
	private final MemberReader memberReader;
	private final InflearnReader inflearnReader;

	@Override
	@Transactional
	public FavoriteInfo.UpdateResponse create(Long memberId, Long lectureId) {
		Member findMember = memberReader.findById(memberId);
		InflearnCourse findInflearnCourse = inflearnReader.findById(lectureId);
		Favorite findFavorite = favoriteReader.findFavoriteByMemberIdAndInflearnCourseId(findMember.getId(),
				findInflearnCourse.getId())
			.orElseGet(() -> createNewFavorite(findMember, findInflearnCourse));
		Favorite saveFavorite = favoriteStore.save(findFavorite);

		return new FavoriteInfo.UpdateResponse(saveFavorite.getId());
	}

	private Favorite createNewFavorite(Member member, InflearnCourse inflearnCourse) {
		Favorite favorite = new Favorite(member, inflearnCourse);

		return favoriteReader.save(favorite);
	}

	@Override
	@Transactional
	public FavoriteInfo.UpdateResponse remove(Long memberId, Long lectureId) {
		boolean memberExists = memberReader.existsById(memberId);
		if (!memberExists) {
			throw new ApiException(MemberErrorCode.NOT_FOUND_MEMBER);
		}
		Favorite findFavorite = favoriteReader.findFavoriteByMemberIdAndInflearnCourseId(memberId, lectureId)
			.map(favoriteResponse -> favoriteReader.findById(favoriteResponse.getId())
				.orElseThrow(FavoriteErrorCode.NOT_FOUND_FAVORITE::throwException))
			.orElseThrow(FavoriteErrorCode.NOT_FOUND_FAVORITE::throwException);
		favoriteStore.delete(findFavorite);

		return new FavoriteInfo.UpdateResponse(findFavorite.getId());
	}

	@Override
	public FavoriteInfo.FindFavoriteListResponse getFavoriteList(Long memberId, PageInfoRequest pageInfoRequest) {
		FavoriteInfo.FindFavoriteListResponse favoriteList = favoriteReader.findList(memberId, pageInfoRequest);

		return favoriteList;
	}
}
