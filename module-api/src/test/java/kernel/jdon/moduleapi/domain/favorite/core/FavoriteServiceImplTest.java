package kernel.jdon.moduleapi.domain.favorite.core;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import kernel.jdon.moduleapi.domain.favorite.error.FavoriteErrorCode;
import kernel.jdon.moduleapi.domain.inflearncourse.error.InflearncourseErrorCode;
import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.page.CustomJpaPageInfo;
import kernel.jdon.moduleapi.global.page.CustomPageInfo;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.favorite.domain.Favorite;
import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import kernel.jdon.moduledomain.member.domain.Gender;
import kernel.jdon.moduledomain.member.domain.Member;
import kernel.jdon.moduledomain.member.domain.MemberAccountStatus;
import kernel.jdon.moduledomain.member.domain.MemberRole;
import kernel.jdon.moduledomain.member.domain.SocialProviderType;

@DisplayName("Favorite Service Impl 테스트")
@ExtendWith(MockitoExtension.class)
class FavoriteServiceImplTest {

	@Mock
	private FavoriteReader favoriteReader;

	@Mock
	private FavoriteFactory favoriteFactory;

	@InjectMocks
	private FavoriteServiceImpl favoriteServiceImpl;

	private static List<FavoriteInfo.FindFavorite> mockFavoriteList() {
		return List.of(
			new FavoriteInfo.FindFavorite(16L, "Next.js 필수 개발 가이드 3시간 완성!",
				"https://www.inflearn.com/course/3시간만에-끝내는-nextjs-개발의-모든것",
				"https://cdn.inflearn.com/public/courses/332438/cover/b9236817-60b9-42e3-b18d-8f517e3c0d93/332438.png",
				"YONGSU JEONG", 0L,
				33000),
			new FavoriteInfo.FindFavorite(23L, "따라하며 배우는 리액트 A-Z", "https://www.inflearn.com/course/따라하는-리액트",
				"https://cdn.inflearn.com/public/courses/329170/cover/223c54c0-9220-4937-836d-70a36be3eb1c/329170-eng.png",
				"John Ahn", 2300L, 55000)
		);
	}

	@DisplayName("1: 유효한 회원 ID와 강의 ID로 새로운 찜을 생성하면, 생성된 찜의 ID를 반환한다.")
	@Test
	void givenValidMemberAndLecture_whenCreateFavorite_thenReturnCreatedFavoriteId() {
		// given
		Member member = mockMember();
		InflearnCourse inflearnCourse = mockInflearnCourse();

		Long memberId = member.getId();
		Long lectureId = inflearnCourse.getId();

		Favorite expectedFavorite = new Favorite(member, inflearnCourse);
		FavoriteInfo.UpdateResponse expectedResponse = mockUpdateResponse(lectureId);

		// when
		when(favoriteFactory.create(memberId, lectureId)).thenReturn(expectedFavorite);
		FavoriteInfo.UpdateResponse actualResponse = favoriteServiceImpl.create(memberId, lectureId);

		// then
		assertThat(actualResponse.getLectureId()).isEqualTo(expectedResponse.getLectureId());
		verify(favoriteFactory, times(1)).create(memberId, lectureId);
	}

	@DisplayName("2: 이미 찜한 강의에 대해 찜하기를 하면, 기존에 찜되어 있는 lectureId를 반환한다.")
	@Test
	void givenExistingFavorite_whenCreateFavorite_thenReturnExistingLectureId() {
		// given
		Member member = mockMember();
		InflearnCourse inflearnCourse = mockInflearnCourse();

		Long memberId = member.getId();
		Long lectureId = inflearnCourse.getId();

		Favorite existingFavorite = new Favorite(member, inflearnCourse);
		FavoriteInfo.UpdateResponse expectedResponse = mockUpdateResponse(lectureId);

		// when
		when(favoriteFactory.create(memberId, lectureId)).thenReturn(existingFavorite);
		FavoriteInfo.UpdateResponse actualResponse = favoriteServiceImpl.create(memberId, lectureId);

		// then
		assertThat(actualResponse.getLectureId()).isEqualTo(expectedResponse.getLectureId());
	}

	@DisplayName("3: 존재하는 찜 정보에 대해 찜 삭제를 하면, 찜 취소한 강의id를 반환한다.")
	@Test
	void givenValidLectureId_whenDeleteFavorite_thenReturnDeletedLectureId() {
		// given
		Member member = mockMember();
		InflearnCourse inflearnCourse = mockInflearnCourse();

		Long memberId = member.getId();
		Long lectureId = inflearnCourse.getId();

		Favorite expectedFavorite = new Favorite(member, inflearnCourse);
		FavoriteInfo.UpdateResponse expectedResponse = mockUpdateResponse(lectureId);

		// when
		when(favoriteFactory.delete(memberId, lectureId)).thenReturn(expectedFavorite);
		FavoriteInfo.UpdateResponse actualResponse = favoriteServiceImpl.remove(memberId, lectureId);

		// then
		assertThat(actualResponse.getLectureId()).isEqualTo(expectedResponse.getLectureId());
		verify(favoriteFactory, times(1)).delete(memberId, lectureId);
	}

	@DisplayName("4: 존재하지 않는 강의에 대해 찜 삭제를 하면, NOT_FOUND_INFLEARN_COURSE 에러를 반환한다.")
	@Test
	void givenInvalidLecture_whenDeleteFavorite_thenReturnNotFoundLectureError() {
		// given
		Long memberId = 1L;
		Long invalidLectureId = 100000000000000L;

		// when
		when(favoriteFactory.delete(memberId, invalidLectureId)).thenThrow(
			new ApiException(InflearncourseErrorCode.NOT_FOUND_INFLEARN_COURSE));

		// then
		ApiException actualException = assertThrows(ApiException.class,
			() -> favoriteServiceImpl.remove(memberId, invalidLectureId));
		assertThat(actualException.getErrorCode()).isEqualTo(InflearncourseErrorCode.NOT_FOUND_INFLEARN_COURSE);
		verify(favoriteFactory, times(1)).delete(memberId, invalidLectureId);
	}

	@DisplayName("5: 존재하지 않는 회원에 대해 찜 삭제를 하면, NOT_FOUND_MEMBER 에러를 반환한다.")
	@Test
	void givenInvalidMember_whenDeleteFavorite_thenReturnNotFoundLectureError() {
		// given
		Long invalidMemberId = 100000000000000L;
		Long lectureId = 1L;

		// when
		when(favoriteFactory.delete(invalidMemberId, lectureId)).thenThrow(
			new ApiException(MemberErrorCode.NOT_FOUND_MEMBER));

		// then
		ApiException actualException = assertThrows(ApiException.class,
			() -> favoriteServiceImpl.remove(invalidMemberId, lectureId));
		assertThat(actualException.getErrorCode()).isEqualTo(MemberErrorCode.NOT_FOUND_MEMBER);
		verify(favoriteFactory, times(1)).delete(invalidMemberId, lectureId);
	}

	@DisplayName("6: 존재하지 않는 찜 정보에 대해 찜 삭제를 하면, NOT_FOUND_FAVORITE 에러를 반환한다.")
	@Test
	void givenInvalidFavorite_whenDeleteFavorite_thenReturnNotFoundFavoriteError() {
		// given
		Long memberId = 1L;
		Long lectureId = 2L;

		// when
		when(favoriteFactory.delete(memberId, lectureId))
			.thenThrow(new ApiException(FavoriteErrorCode.NOT_FOUND_FAVORITE));

		// then
		ApiException actualException = assertThrows(ApiException.class,
			() -> favoriteServiceImpl.remove(memberId, lectureId));
		assertThat(actualException.getErrorCode()).isEqualTo(FavoriteErrorCode.NOT_FOUND_FAVORITE);
		verify(favoriteFactory, times(1)).delete(memberId, lectureId);
	}

	@DisplayName("7: 유효한 회원과 페이지 정보로 찜 목록을 조회하면, 해당하는 찜 목록과 페이지 정보를 반환한다.")
	@Test
	void givenValidMemberAndPageInfo_whenGetFavoriteList_thenReturnFavoriteList() {
		// given
		Long memberId = 1L;
		PageInfoRequest pageInfoRequest = new PageInfoRequest(0, 12);
		List<FavoriteInfo.FindFavorite> expectedFavorites = mockFavoriteList();
		CustomPageInfo customPageInfo = new CustomJpaPageInfo(
			new PageImpl<>(expectedFavorites, PageRequest.of(0, 12), expectedFavorites.size()));
		FavoriteInfo.FindFavoriteListResponse expectedResponse = new FavoriteInfo.FindFavoriteListResponse(
			expectedFavorites,
			customPageInfo
		);

		// when
		when(favoriteReader.findList(memberId, pageInfoRequest)).thenReturn(expectedResponse);
		FavoriteInfo.FindFavoriteListResponse actualResponse = favoriteServiceImpl.getFavoriteList(memberId,
			pageInfoRequest);

		// then
		assertThat(actualResponse.getContent()).hasSameSizeAs(expectedFavorites);
		assertThat(actualResponse.getPageInfo().getPageNumber()).isEqualTo(customPageInfo.getPageNumber());
		assertThat(actualResponse.getPageInfo().getPageSize()).isEqualTo(customPageInfo.getPageSize());
		assertThat(actualResponse.getContent()).isEqualTo(expectedFavorites);
		verify(favoriteReader, times(1)).findList(memberId, pageInfoRequest);
	}

	private Member mockMember() {
		return Member.builder()
			.email("이메일@kakao.com")
			.nickname("닉네임")
			.birth("1990-01-01")
			.gender(Gender.MALE)
			.joinDate(LocalDateTime.now())
			.role(MemberRole.ROLE_USER)
			.accountStatus(MemberAccountStatus.ACTIVE)
			.socialProvider(SocialProviderType.KAKAO)
			.build();
	}

	private InflearnCourse mockInflearnCourse() {
		return InflearnCourse.builder()
			.courseId(328238L)
			.title("자바스크립트 : 기초부터 실전까지 올인원")
			.lectureUrl("https://www.inflearn.com/course/자바스크립트-올인원")
			.instructor("코딩알려주는누나")
			.studentCount(500L)
			.imageUrl(
				"https://cdn.inflearn.com/public/courses/328238/cover/f3821bcd-cac4-470a-b3bc-edc8d699a604/328238-eng.png")
			.price(81400)
			.build();
	}

	private FavoriteInfo.UpdateResponse mockUpdateResponse(Long lectureId) {
		return FavoriteInfo.UpdateResponse.builder()
			.lectureId(lectureId)
			.build();
	}

}