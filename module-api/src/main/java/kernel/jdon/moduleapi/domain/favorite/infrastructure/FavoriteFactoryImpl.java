package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.favorite.core.FavoriteFactory;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteReader;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteStore;
import kernel.jdon.moduleapi.domain.favorite.error.FavoriteErrorCode;
import kernel.jdon.moduleapi.domain.inflearncourse.core.InflearnReader;
import kernel.jdon.moduleapi.domain.member.core.MemberReader;
import kernel.jdon.moduledomain.favorite.domain.Favorite;
import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import kernel.jdon.moduledomain.member.domain.Member;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FavoriteFactoryImpl implements FavoriteFactory {
	private final FavoriteReader favoriteReader;
	private final FavoriteStore favoriteStore;
	private final MemberReader memberReader;
	private final InflearnReader inflearnReader;

	@Override
	public Favorite create(Long memberId, Long lectureId) {
		final Member findMember = memberReader.findById(memberId);
		final InflearnCourse findInflearnCourse = inflearnReader.findById(lectureId);
		final Favorite findFavorite = favoriteReader.findFavoriteByMemberIdAndInflearnCourseId(findMember.getId(),
				findInflearnCourse.getId())
			.orElseGet(() -> createNewFavorite(findMember, findInflearnCourse));
		final Favorite saveFavorite = favoriteStore.save(findFavorite);

		return saveFavorite;
	}

	private Favorite createNewFavorite(final Member member, final InflearnCourse inflearnCourse) {
		final Favorite favorite = new Favorite(member, inflearnCourse);

		return favoriteReader.save(favorite);
	}

	@Override
	public Favorite delete(Long memberId, Long lectureId) {
		final Member findMember = memberReader.findById(memberId);
		final InflearnCourse findInflearnCourse = inflearnReader.findById(lectureId);
		final Favorite findFavorite = favoriteReader.findFavoriteByMemberIdAndInflearnCourseId(findMember.getId(),
				findInflearnCourse.getId())
			.orElseThrow(FavoriteErrorCode.NOT_FOUND_FAVORITE::throwException);
		favoriteStore.delete(findFavorite);

		return findFavorite;
	}
}
