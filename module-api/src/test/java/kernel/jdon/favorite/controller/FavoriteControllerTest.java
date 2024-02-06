package kernel.jdon.favorite.controller;

import static org.hamcrest.collection.IsCollectionWithSize.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import kernel.jdon.dto.response.CommonResponse;
import kernel.jdon.favorite.dto.request.UpdateFavoriteRequest;
import kernel.jdon.favorite.dto.response.FindFavoriteResponse;
import kernel.jdon.favorite.dto.response.UpdateFavoriteResponse;
import kernel.jdon.favorite.service.FavoriteService;
import kernel.jdon.global.page.CustomPageResponse;
import kernel.jdon.inflearncourse.domain.InflearnCourse;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FavoriteController.class)
class FavoriteControllerTest {

	@MockBean
	private FavoriteService favoriteService;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String GET_FAVORITE_LIST_URL = "/api/v1/favorites";
	private static final String UPDATE_FAVORITE_COURSE_URL = "/api/v1/favorites";
	private static final String UPDATE_FAVORITE_COURSE_REDIRECT_PREFIX = "/api/v1/favorites/";

	@WithMockUser
	@DisplayName("유효한 사용자의 경우 찜한 목록을 조회한다.")
	@Test
	void givenValidRequest_whenFindfavoriteCourseList_thenReturnFindFavoriteCourseList() throws Exception {
		// given
		PageRequest expectedPageRequest = PageRequest.of(0, 12);

		List<FindFavoriteResponse> findFavoriteResponseList = createFavorites();
		int favoriteListSize = findFavoriteResponseList.size();
		PageImpl<FindFavoriteResponse> pageImpl = new PageImpl<>(findFavoriteResponseList, expectedPageRequest,
			favoriteListSize);
		CommonResponse expectedResponse = CommonResponse.of(CustomPageResponse.of(pageImpl));

		given(favoriteService.findList(anyLong(), eq(expectedPageRequest))).willReturn(
			CustomPageResponse.of(pageImpl));

		// when
		ResultActions resultActions = mockMvc.perform(get(GET_FAVORITE_LIST_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.param("page", "0")
			.param("size", "12"));

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			// case 1
			.andExpect(content().json(objectMapper.writeValueAsString(expectedResponse), true))
			// case 2
			.andExpect(jsonPath("$.data.content[0].lectureId").value(findFavoriteResponseList.get(0).getLectureId()))
			.andExpect(jsonPath("$.data.content[0].title").value(findFavoriteResponseList.get(0).getTitle()))
			.andExpect(jsonPath("$.data.content[0].lectureUrl").value(findFavoriteResponseList.get(0).getLectureUrl()))
			.andExpect(jsonPath("$.data.content[0].imageUrl").value(findFavoriteResponseList.get(0).getImageUrl()))
			.andExpect(jsonPath("$.data.content[0].instructor").value(findFavoriteResponseList.get(0).getInstructor()))
			.andExpect(
				jsonPath("$.data.content[0].studentCount").value(findFavoriteResponseList.get(0).getStudentCount()))
			.andExpect(jsonPath("$.data.content[0].price").value(findFavoriteResponseList.get(0).getPrice()))
			.andExpect(jsonPath("$.data.content.length()").value(favoriteListSize));
		then(favoriteService).should(times(1)).findList(anyLong(), eq(expectedPageRequest));
	}

	@WithMockUser
	@DisplayName("페이지네이션이 주어졌을 때 찜 목록을 조회한다.")
	@Test
	void givenPagination_whenFindFavoriteCourseList_thenReturnsPagedFavoriteCourseList() throws Exception {
		// given
		int page = 0;
		int size = 10;
		PageRequest pageRequest = PageRequest.of(page, size);

		List<FindFavoriteResponse> findFavoriteResponseList = createPageTestFavorites(size);

		PageImpl<FindFavoriteResponse> findFavoriteResponsePage = new PageImpl<>(findFavoriteResponseList, pageRequest,
			50);
		CustomPageResponse<FindFavoriteResponse> customPageResponse = CustomPageResponse.of(findFavoriteResponsePage);
		CommonResponse<CustomPageResponse<FindFavoriteResponse>> expectedResponse = CommonResponse.of(
			customPageResponse);

		given(favoriteService.findList(anyLong(), eq(pageRequest))).willReturn(customPageResponse);

		// when
		ResultActions resultActions = mockMvc.perform(get(GET_FAVORITE_LIST_URL)
			.param("page", String.valueOf(page))
			.param("size", String.valueOf(size))
			.with(csrf())
			.contentType(MediaType.APPLICATION_JSON));

		// then
		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data.content", hasSize(size)))
			.andExpect(jsonPath("$.data.pageInfo.pageNumber").value(page))
			.andExpect(jsonPath("$.data.pageInfo.pageSize").value(size))
			.andExpect(jsonPath("$.data.pageInfo.totalPages").value(5))
			.andExpect(jsonPath("$.data.pageInfo.first").value(true))
			.andExpect(jsonPath("$.data.pageInfo.last").value(false))
			.andExpect(jsonPath("$.data.pageInfo.empty").value(false))
			.andExpect(
				jsonPath("$.data.content[0].lectureId").value(findFavoriteResponseList.get(0).getLectureId()))
			.andExpect(jsonPath("$.data.content[0].title").value(findFavoriteResponseList.get(0).getTitle()));

		then(favoriteService).should().findList(anyLong(), eq(pageRequest));
	}

	@WithMockUser
	@DisplayName("유효한 요청으로 커피챗 수정 성공 시 수정된 찜한 또는 찜 취소한 강의의 ID를 반환한다.")
	@Test
	void givenValidRequest_whenUpdateFavorite_thenReturnUpdatedFavorite() throws Exception {
		// given
		Long lectureId = 1L;
		Boolean isFavorite = true;
		UpdateFavoriteRequest updateFavoriteRequest = createUpdateFavoriteRequest(lectureId, isFavorite);
		UpdateFavoriteResponse updateFavoriteResponse = UpdateFavoriteResponse.of(lectureId);
		CommonResponse expectedResponse = CommonResponse.of(updateFavoriteResponse);

		given(favoriteService.update(anyLong(), any(UpdateFavoriteRequest.class))).willReturn(
			updateFavoriteResponse);

		// when
		ResultActions resultActions = mockMvc.perform(post(UPDATE_FAVORITE_COURSE_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(updateFavoriteRequest))
			.with(csrf()));

		// then
		resultActions
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", UPDATE_FAVORITE_COURSE_REDIRECT_PREFIX + lectureId))
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			// case 1
			.andExpect(content().json(objectMapper.writeValueAsString(expectedResponse), true))
			// case 2
			.andExpect(jsonPath("$.data.lectureId").value(lectureId));

		then(favoriteService).should(times(1)).update(any(Long.class), any(UpdateFavoriteRequest.class));
	}

	private List<FindFavoriteResponse> createFavorites() {
		List<InflearnCourse> inflearnCourseList = List.of(
			InflearnCourse.builder()
				.courseId(328238L)
				.title("자바스크립트 : 기초부터 실전까지 올인원")
				.lectureUrl("https://www.inflearn.com/course/자바스크립트-올인원")
				.imageUrl(
					"https://cdn.inflearn.com/public/courses/328238/cover/f3821bcd-cac4-470a-b3bc-edc8d699a604/328238-eng.png")
				.instructor("코딩알려주는누나")
				.studentCount(500L)
				.price(81400)
				.build(),
			InflearnCourse.builder()
				.courseId(332113L)
				.title("따라하며 배우는 자바스크립트 A-Z")
				.lectureUrl("https://www.inflearn.com/course/따라하며-배우는-자바스크립트")
				.imageUrl(
					"https://cdn.inflearn.com/public/courses/332113/cover/2e916cc7-0294-4db6-ae97-8acae5f1d512/332113-eng.png")
				.instructor("John Ahn")
				.studentCount(200L)
				.price(55000)
				.build()
		);

		return inflearnCourseList.stream()
			.map(FindFavoriteResponse::of)
			.toList();
	}

	private List<FindFavoriteResponse> createPageTestFavorites(int size) {
		return IntStream.range(0, size)
			.mapToObj(i -> new FindFavoriteResponse(
				(long)i,
				"강의 제목 " + i,
				"강의 URL " + i,
				"이미지 URL " + i,
				"지식 공유자 " + i,
				100L * i,
				10000 * i
			))
			.toList();
	}

	private UpdateFavoriteRequest createUpdateFavoriteRequest(Long lectureId, Boolean isFavorite) {
		return UpdateFavoriteRequest.builder()
			.lectureId(lectureId)
			.isFavorite(isFavorite)
			.build();
	}
}