package kernel.jdon.moduleapi.domain.favorite.presentation;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityManager;
import kernel.jdon.moduleapi.domain.favorite.error.FavoriteErrorCode;
import kernel.jdon.moduleapi.domain.inflearncourse.error.InflearncourseErrorCode;
import kernel.jdon.moduleapi.global.dto.SessionUserInfo;
import kernel.jdon.moduledomain.favorite.domain.Favorite;
import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import kernel.jdon.moduledomain.member.domain.Gender;
import kernel.jdon.moduledomain.member.domain.Member;
import kernel.jdon.moduledomain.member.domain.MemberAccountStatus;
import kernel.jdon.moduledomain.member.domain.MemberRole;
import kernel.jdon.moduledomain.member.domain.SocialProviderType;

@DisplayName("Favorite Controller 통합 테스트")
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class FavoriteControllerIntegrationTest {
	private static final String FAVORITE_URL = "/api/v1/favorites";
	private static final String USER_ID = "USER";
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private MockMvc mockMvc;
	private MockHttpSession mockSession;
	private InflearnCourse inflearnCourse;
	private Favorite favorite;

	@BeforeEach
	void setUp() {
		Member member = mockMember();
		entityManager.persist(member);

		SessionUserInfo mockUserInfo = SessionUserInfo.builder()
			.id(member.getId())
			.email(member.getEmail())
			.oauthId("oauth 아이디")
			.socialProvider(member.getSocialProvider())
			.lastLoginDate(member.getLastLoginDate())
			.role(member.getRole())
			.build();

		inflearnCourse = mockInflearnCourse(1L);
		entityManager.persist(inflearnCourse);

		favorite = new Favorite(member, inflearnCourse);
		entityManager.persist(favorite);

		mockSession = new MockHttpSession();
		mockSession.setAttribute(USER_ID, mockUserInfo);
	}

	@WithMockUser
	@DisplayName("1: 유효한 요청이 주어졌을 때 찜한 강의 목록을 조회하면 찜한 강의 목록을 반환한다.")
	@Test
	void givenValidRequest_whenFindfavoriteCourseList_thenReturnFindFavoriteCourseList() throws Exception {
		// when & then
		InflearnCourse firstItem = favorite.getInflearnCourse();

		mockMvc.perform(get(FAVORITE_URL)
				.session(mockSession)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data.content[0].lectureId").value(firstItem.getId()))
			.andExpect(jsonPath("$.data.content[0].title").value(firstItem.getTitle()))
			.andExpect(jsonPath("$.data.content[0].lectureUrl").value(firstItem.getLectureUrl()))
			.andExpect(jsonPath("$.data.content[0].imageUrl").value(firstItem.getImageUrl()))
			.andExpect(jsonPath("$.data.content[0].instructor").value(firstItem.getInstructor()))
			.andExpect(jsonPath("$.data.content[0].studentCount").value(firstItem.getStudentCount()))
			.andExpect(jsonPath("$.data.content[0].price").value(firstItem.getPrice()))
			.andExpect(jsonPath("$.data.content.length()").value(1))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andDo(print());
	}

	@WithMockUser
	@DisplayName("2: 유효한 요청으로 찜한 강의 수정 성공 시 수정된 찜한 또는 찜 취소한 강의의 ID를 반환한다.")
	@Test
	void givenValidRequest_whenUpdateFavorite_thenReturnUpdatedFavorite() throws Exception {
		// given
		boolean isFavorite = false;
		FavoriteDto.UpdateRequest updateRequest = createUpdateRequest(inflearnCourse.getId(), isFavorite);

		// when & then
		mockMvc.perform(post(FAVORITE_URL)
				.session(mockSession)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateRequest)))
			.andExpect(status().isCreated())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data.lectureId").value(inflearnCourse.getId()))
			.andDo(print());
	}

	@WithMockUser
	@DisplayName("3: 존재하지 않는 강의 id로 찜 정보 업데이트 시 NOT_FOUND_INFLEARN_COURSE 에러를 반환한다.")
	@Test
	void givenInvalidFavoriteId_whenUpdateFavorite_thenThrowError() throws Exception {
		// given
		boolean isFavorite = true;
		FavoriteDto.UpdateRequest updateRequest = createUpdateRequest(inflearnCourse.getId() + 1, isFavorite);

		// when & then
		mockMvc.perform(post(FAVORITE_URL)
				.session(mockSession)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateRequest)))
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").value(InflearncourseErrorCode.NOT_FOUND_INFLEARN_COURSE.getMessage()))
			.andDo(print());
	}

	@WithMockUser
	@DisplayName("4: 존재하지만 찜하지 않은 강의를 찜 취소 시 LECTURE_NOT_FAVORITED 에러를 반환한다.")
	@Test
	void givenUnfavoritedLecture_whenUnfavorite_thenThrowError() throws Exception {
		// given
		InflearnCourse notFavoritedinflearnCourse = mockInflearnCourse(2L);
		entityManager.persist(notFavoritedinflearnCourse);

		boolean isFavorite = false;
		FavoriteDto.UpdateRequest updateRequest = createUpdateRequest(notFavoritedinflearnCourse.getId(), isFavorite);

		// when & then
		mockMvc.perform(post(FAVORITE_URL)
				.session(mockSession)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateRequest)))
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").value(FavoriteErrorCode.NOT_FOUND_FAVORITE.getMessage()))
			.andDo(print());
	}

	private FavoriteDto.UpdateRequest createUpdateRequest(Long lectureId, Boolean isFavorite) {
		return FavoriteDto.UpdateRequest.builder()
			.lectureId(lectureId)
			.isFavorite(isFavorite)
			.build();
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

	private InflearnCourse mockInflearnCourse(Long courseId) {
		return InflearnCourse.builder()
			.courseId(courseId)
			.title("강의 제목")
			.lectureUrl("https://www.inflearn.com/course/강의 제목")
			.instructor("지식 공유자")
			.studentCount(500L)
			.imageUrl(
				"https://cdn.inflearn.com/public/courses/courseId/cover/picture.png")
			.price(80000)
			.build();
	}
}
