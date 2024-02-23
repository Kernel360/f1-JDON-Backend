package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteStore;
import kernel.jdon.moduleapi.domain.favorite.error.FavoriteErrorCode;
import lombok.RequiredArgsConstructor;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteStoreImpl implements FavoriteStore {
	private final FavoriteRepository favoriteRepository;

	@Transactional
	@Override
	public Favorite save(Member member, InflearnCourse inflearnCourse) {
		return favoriteRepository.findFavoriteByMemberIdAndInflearnCourseId(member.getId(), inflearnCourse.getId())
			.orElseGet(() -> createNewFavorite(member, inflearnCourse));
	}

	private Favorite createNewFavorite(Member member, InflearnCourse inflearnCourse) {
		Favorite favorite = new Favorite(member, inflearnCourse);
		Favorite savedFavorite = favoriteRepository.save(favorite);

		return savedFavorite;
	}

	@Transactional
	@Override
	public Favorite delete(Long memberId, Long lectureId) {
		Favorite findFavorite = favoriteRepository.findFavoriteByMemberIdAndInflearnCourseId(memberId, lectureId)
			.map(favoriteResponse -> favoriteRepository.findById(favoriteResponse.getId())
				.orElseThrow(FavoriteErrorCode.NOT_FOUND_FAVORITE::throwException))
			.orElseThrow(FavoriteErrorCode.NOT_FOUND_FAVORITE::throwException);

		favoriteRepository.delete(findFavorite);

		return findFavorite;
	}

}
