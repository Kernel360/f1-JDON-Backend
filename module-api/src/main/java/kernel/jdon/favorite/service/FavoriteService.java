package kernel.jdon.favorite.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.validation.Valid;
import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.favorite.dto.request.UpdateFavoriteRequest;
import kernel.jdon.favorite.dto.response.FindFavoriteResponse;
import kernel.jdon.favorite.dto.response.UpdateFavoriteResponse;
import kernel.jdon.favorite.error.FavoriteErrorCode;
import kernel.jdon.favorite.repository.FavoriteRepository;
import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.inflearncourse.error.InflearncourseErrorCode;
import kernel.jdon.inflearncourse.repository.InflearnCourseRepository;
import kernel.jdon.member.domain.Member;
import kernel.jdon.member.error.MemberErrorCode;
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
	public UpdateFavoriteResponse update(Long memberId, @Valid UpdateFavoriteRequest updateFavoriteRequest) {
		if (updateFavoriteRequest.getIsFavorite()) {
			return create(memberId, updateFavoriteRequest);
		} else {
			return delete(memberId, updateFavoriteRequest);
		}
	}

	public UpdateFavoriteResponse create(Long memberId, @Valid UpdateFavoriteRequest updateFavoriteRequest) {
		Member findMember = memberRepository.findById(memberId)
			.orElseThrow(() -> new ApiException(MemberErrorCode.NOT_FOUND_MEMBER));
		InflearnCourse findInflearnCourse = inflearnCourseRepository.findById(
				updateFavoriteRequest.getLectureId())
			.orElseThrow(() -> new ApiException(InflearncourseErrorCode.NOT_FOUND_INFLEARN_COURSE));

		return favoriteRepository.findFavoriteByMemberIdAndInflearnCourseId(memberId,
				updateFavoriteRequest.getLectureId())
			.map(favorite -> UpdateFavoriteResponse.of(favorite.getId()))
			.orElseGet(() -> createNewFavorite(findMember, findInflearnCourse));
	}

	private UpdateFavoriteResponse createNewFavorite(Member member, InflearnCourse inflearnCourse) {
		Favorite favorite = new Favorite(member, inflearnCourse);
		Favorite savedFavorite = favoriteRepository.save(favorite);

		return UpdateFavoriteResponse.of(savedFavorite.getId());
	}

	public UpdateFavoriteResponse delete(Long memberId, @Valid UpdateFavoriteRequest updateFavoriteRequest) {
		Favorite findFavorite = favoriteRepository.findFavoriteByMemberIdAndInflearnCourseId(memberId,
				updateFavoriteRequest.getLectureId())
			.orElseThrow(() -> new ApiException(FavoriteErrorCode.LECTURE_NOT_FAVORITED));

		favoriteRepository.delete(findFavorite);

		return UpdateFavoriteResponse.of(findFavorite.getId());
	}

	public CustomPageResponse findList(Long memberId, Pageable pageable) {
		Page<Favorite> findFavoritePage = favoriteRepository.findFavoriteByMemberId(memberId, pageable);
		Page<FindFavoriteResponse> findFavoriteResponsePage = findFavoritePage.map(
			favorite -> FindFavoriteResponse.of(favorite.getInflearnCourse())
		);

		return CustomPageResponse.of(findFavoriteResponsePage);
	}
}
