package kernel.jdon.favorite.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.error.code.api.MemberErrorCode;
import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.favorite.dto.object.CreateFavoriteDto;
import kernel.jdon.favorite.dto.request.UpdateFavoriteRequest;
import kernel.jdon.favorite.dto.response.CreateFavoriteResponse;
import kernel.jdon.favorite.dto.response.DeleteFavoriteResponse;
import kernel.jdon.favorite.error.FavoriteErrorCode;
import kernel.jdon.favorite.repository.FavoriteRepository;
import kernel.jdon.global.exception.ApiException;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.inflearncourse.error.InflearncourseErrorCode;
import kernel.jdon.inflearncourse.repository.InflearnCourseRepository;
import kernel.jdon.member.domain.Member;
import kernel.jdon.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoriteService {
	private final FavoriteRepository favoriteRepository;
	private final MemberRepository memberRepository;
	private final InflearnCourseRepository inflearnCourseRepository;

	@Transactional
	public CreateFavoriteResponse create(Long memberId, UpdateFavoriteRequest updateFavoriteRequest) {
		Member findMember = memberRepository.findById(memberId)
			.orElseThrow(() -> new ApiException(MemberErrorCode.NOT_FOUND_MEMBER));
		InflearnCourse findInflearnCourse = inflearnCourseRepository.findById(
				updateFavoriteRequest.getLectureId())
			.orElseThrow(() -> new ApiException(InflearncourseErrorCode.NOT_FOUND_INFLEARN_COURSE));

		return favoriteRepository.findFavoriteByMemberIdAndInflearnCourseId(memberId,
				updateFavoriteRequest.getLectureId())
			.map(this::activateFavorite)
			.orElseGet(() -> createNewFavorite(findMember, findInflearnCourse));
	}

	private CreateFavoriteResponse createNewFavorite(Member member, InflearnCourse inflearnCourse) {
		CreateFavoriteDto createFavoriteDto = CreateFavoriteDto.builder()
			.member(member)
			.inflearnCourse(inflearnCourse)
			.build();
		Favorite savedFavorite = favoriteRepository.save(UpdateFavoriteRequest.toEntity(createFavoriteDto));

		return CreateFavoriteResponse.of(savedFavorite.getId());
	}

	private CreateFavoriteResponse activateFavorite(Favorite favorite) {
		if (favorite.isDeleted()) {
			favorite.like();
		}

		return CreateFavoriteResponse.of(favorite.getId());
	}

	@Transactional
	public DeleteFavoriteResponse delete(Long memberId, UpdateFavoriteRequest updateFavoriteRequest) {
		Favorite findFavorite = favoriteRepository.findFavoriteByMemberIdAndInflearnCourseId(memberId,
				updateFavoriteRequest.getLectureId())
			.orElseThrow(() -> new ApiException(FavoriteErrorCode.NOT_FOUND_FAVORITE));

		findFavorite.dislike();

		return DeleteFavoriteResponse.of(findFavorite.getId());
	}
}
