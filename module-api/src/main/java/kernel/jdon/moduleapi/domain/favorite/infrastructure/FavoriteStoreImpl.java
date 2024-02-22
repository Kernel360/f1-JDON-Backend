package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfo;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteStore;
import kernel.jdon.moduleapi.domain.favorite.error.FavoriteErrorCode;
import kernel.jdon.moduleapi.domain.inflearncourse.infrastructure.InflearnCourseRepository;
import kernel.jdon.moduleapi.domain.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteStoreImpl implements FavoriteStore {
	private final FavoriteRepository favoriteRepository;
	private final MemberRepository memberRepository;
	private final InflearnCourseRepository inflearnCourseRepository;
	
	@Transactional
	@Override
	public FavoriteInfo.UpdateResponse create(Member member, InflearnCourse inflearnCourse) {

		return favoriteRepository.findFavoriteByMemberIdAndInflearnCourseId(member.getId(), inflearnCourse.getId())
			.map(favorite -> new FavoriteInfo.UpdateResponse(favorite.getId()))
			.orElseGet(() -> createNewFavorite(member, inflearnCourse));
	}

	private FavoriteInfo.UpdateResponse createNewFavorite(Member member, InflearnCourse inflearnCourse) {
		Favorite favorite = new Favorite(member, inflearnCourse);
		Favorite savedFavorite = favoriteRepository.save(favorite);

		return new FavoriteInfo.UpdateResponse(savedFavorite.getId());
	}

	@Transactional
	@Override
	public void delete(Long memberId, Long lectureId) {
		Favorite favorite = favoriteRepository.findFavoriteByMemberIdAndInflearnCourseId(memberId, lectureId)
			.map(favoriteResponse -> {
				return favoriteRepository.findById(favoriteResponse.getId())
					.orElseThrow(FavoriteErrorCode.NOT_FOUND_FAVORITE::throwException);
			})
			.orElseThrow(FavoriteErrorCode.NOT_FOUND_FAVORITE::throwException);

		favoriteRepository.delete(favorite);
	}

}
