package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.favorite.application.FavoriteStore;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteCommand;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfo;
import kernel.jdon.moduleapi.domain.favorite.error.FavoriteErrorCode;
import kernel.jdon.moduleapi.domain.inflearncourse.error.InflearncourseErrorCode;
import kernel.jdon.moduleapi.domain.inflearncourse.infrastructure.InflearnCourseRepository;
import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import kernel.jdon.moduleapi.domain.member.infrastructure.MemberRepository;
import kernel.jdon.moduleapi.global.exception.ApiException;
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
	public FavoriteInfo.UpdateResponse update(Long memberId, @Valid FavoriteCommand.UpdateRequest command) {
		if (command.getIsFavorite()) {
			return create(memberId, command);
		}
		return delete(memberId, command);
	}

	private FavoriteInfo.UpdateResponse create(Long memberId, @Valid FavoriteCommand.UpdateRequest command) {
		Member findMember = memberRepository.findById(memberId)
			.orElseThrow(() -> new ApiException(MemberErrorCode.NOT_FOUND_MEMBER));
		InflearnCourse findInflearnCourse = inflearnCourseRepository.findById(
				command.getLectureId())
			.orElseThrow(() -> new ApiException(InflearncourseErrorCode.NOT_FOUND_INFLEARN_COURSE));
		return favoriteRepository.findFavoriteByMemberIdAndInflearnCourseId(memberId, command.getLectureId())
			.map(favorite -> new FavoriteInfo.UpdateResponse(favorite.getId()))
			.orElseGet(() -> createNewFavorite(findMember, findInflearnCourse));
	}

	private FavoriteInfo.UpdateResponse createNewFavorite(Member member, InflearnCourse inflearnCourse) {
		Favorite favorite = new Favorite(member, inflearnCourse);
		Favorite savedFavorite = favoriteRepository.save(favorite);

		return new FavoriteInfo.UpdateResponse(savedFavorite.getId());
	}

	private FavoriteInfo.UpdateResponse delete(Long memberId, FavoriteCommand.UpdateRequest command) {
		Favorite favorite = favoriteRepository.findFavoriteByMemberIdAndInflearnCourseId(memberId,
				command.getLectureId())
			.map(favoriteResponse -> {
				return favoriteRepository.findById(favoriteResponse.getId())
					.orElseThrow(() -> new ApiException(FavoriteErrorCode.NOT_FOUND_FAVORITE));
			})
			.orElseThrow(() -> new ApiException(FavoriteErrorCode.NOT_FOUND_FAVORITE));

		favoriteRepository.delete(favorite);

		return new FavoriteInfo.UpdateResponse(favorite.getId());
	}

}
