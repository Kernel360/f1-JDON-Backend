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

import kernel.jdon.favorite.dto.request.UpdateFavoriteRequest;
import kernel.jdon.favorite.dto.response.FindFavoriteResponse;
import kernel.jdon.favorite.dto.response.UpdateFavoriteResponse;
import kernel.jdon.favorite.error.FavoriteErrorCode;
import kernel.jdon.favorite.service.FavoriteService;
import kernel.jdon.global.exception.ApiException;
import kernel.jdon.global.page.CustomPageResponse;

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
	@DisplayName("유효한 요청이 주어졌을 때 찜한 강의 목록을 조회하면 찜한 강의 목록을 반환한다.")
	@Test
	void givenValidRequest_whenFindfavoriteCourseList_thenReturnFindFavoriteCourseList() throws Exception {
		// given
		PageRequest expectedPageRequest = PageRequest.of(0, 12);

		List<FindFavoriteResponse> findFavoriteResponseList = createFavorites();
		int favoriteListSize = findFavoriteResponseList.size();
		PageImpl<FindFavoriteResponse> pageImpl = new PageImpl<>(findFavoriteResponseList, expectedPageRequest,
			favoriteListSize);

		given(favoriteService.findList(anyLong(), eq(expectedPageRequest))).willReturn(
			CustomPageResponse.of(pageImpl));

		// when
		ResultActions resultActions = mockMvc.perform(get(GET_FAVORITE_LIST_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.param("page", "0")
			.param("size", "12"));

		// then
		FindFavoriteResponse firstItem = findFavoriteResponseList.get(0);

		resultActions
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.data.content[0].lectureId").value(firstItem.getLectureId()))
			.andExpect(jsonPath("$.data.content[0].title").value(firstItem.getTitle()))
			.andExpect(jsonPath("$.data.content[0].lectureUrl").value(firstItem.getLectureUrl()))
			.andExpect(jsonPath("$.data.content[0].imageUrl").value(firstItem.getImageUrl()))
			.andExpect(jsonPath("$.data.content[0].instructor").value(firstItem.getInstructor()))
			.andExpect(jsonPath("$.data.content[0].studentCount").value(firstItem.getStudentCount()))
			.andExpect(jsonPath("$.data.content[0].price").value(firstItem.getPrice()))
			.andExpect(jsonPath("$.data.content.length()").value(favoriteListSize));
		then(favoriteService).should(times(1)).findList(anyLong(), eq(expectedPageRequest));
	}

	@WithMockUser
	@DisplayName("페이지네이션이 포함된 유효한 요청이 주어졌을 때 찜한 강의 목록을 조회하면 찜한 강의 목록을 반환한다.")
	@Test
	void givenPagination_whenFindFavoriteCourseList_thenReturnPagedFavoriteCourseList() throws Exception {
		// given
		int page = 0;
		int size = 10;
		PageRequest pageRequest = PageRequest.of(page, size);

		List<FindFavoriteResponse> findFavoriteResponseList = createPageTestFavorites(size);
		PageImpl<FindFavoriteResponse> findFavoriteResponsePage = new PageImpl<>(findFavoriteResponseList, pageRequest,
			50);
		CustomPageResponse<FindFavoriteResponse> customPageResponse = CustomPageResponse.of(findFavoriteResponsePage);

		given(favoriteService.findList(anyLong(), eq(pageRequest))).willReturn(customPageResponse);

		// when
		ResultActions resultActions = mockMvc.perform(get(GET_FAVORITE_LIST_URL)
			.param("page", String.valueOf(page))
			.param("size", String.valueOf(size))
			.with(csrf())
			.contentType(MediaType.APPLICATION_JSON));

		// then
		FindFavoriteResponse firstItem = findFavoriteResponseList.get(0);

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
				jsonPath("$.data.content[0].lectureId").value(firstItem.getLectureId()))
			.andExpect(jsonPath("$.data.content[0].title").value(firstItem.getTitle()));

		then(favoriteService).should().findList(anyLong(), eq(pageRequest));
	}

	@WithMockUser
	@DisplayName("유효한 요청으로 찜한 강의 수정 성공 시 수정된 찜한 또는 찜 취소한 강의의 ID를 반환한다.")
	@Test
	void givenValidRequest_whenUpdateFavorite_thenReturnUpdatedFavorite() throws Exception {
		// given
		Long lectureId = 1L;
		Boolean isFavorite = true;
		UpdateFavoriteRequest updateFavoriteRequest = createUpdateFavoriteRequest(lectureId, isFavorite);
		UpdateFavoriteResponse updateFavoriteResponse = UpdateFavoriteResponse.of(lectureId);

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
			.andExpect(jsonPath("$.data.lectureId").value(lectureId));

		then(favoriteService).should(times(1)).update(any(Long.class), any(UpdateFavoriteRequest.class));
	}

	@WithMockUser
	@DisplayName("존재하지 않는 강의 id로 찜 정보 업데이트 시 NOT_FOUND_FAVORITE 에러를 반환한다.")
	@Test
	void givenInvalidFavoriteId_whenUpdateFavorite_thenThrowError() throws Exception {
		// given
		Long invalidLectureId = 999L;
		Boolean isFavorite = true;
		UpdateFavoriteRequest updateFavoriteRequest = createUpdateFavoriteRequest(invalidLectureId, isFavorite);

		given(favoriteService.update(anyLong(), any(UpdateFavoriteRequest.class)))
			.willThrow(new ApiException(FavoriteErrorCode.NOT_FOUND_FAVORITE));

		// when
		ResultActions resultActions = mockMvc.perform(post(UPDATE_FAVORITE_COURSE_URL)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(updateFavoriteRequest))
			.with(csrf()));

		// then
		resultActions
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message").value(FavoriteErrorCode.NOT_FOUND_FAVORITE.getMessage()));

		then(favoriteService).should().update(anyLong(), any(UpdateFavoriteRequest.class));
	}

	@WithMockUser
	@DisplayName("존재하지만 찜하지 않은 강의를 찜 취소 시 LECTURE_NOT_FAVORITED 에러를 반환한다.")
	@Test
	void givenUnfavoritedLecture_whenUnfavorite_thenThrowError() throws Exception {
		// given
		Long lectureId = 1L;
		Boolean isFavorite = false;
		UpdateFavoriteRequest updateFavoriteRequest = createUpdateFavoriteRequest(lectureId, isFavorite);

		given(favoriteService.update(anyLong(), any(UpdateFavoriteRequest.class)))
			.willThrow(new ApiException(FavoriteErrorCode.LECTURE_NOT_FAVORITED));

		// when
		ResultActions resultActions = mockMvc.perform(
			post(UPDATE_FAVORITE_COURSE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateFavoriteRequest))
				.with(csrf())
		);

		// then
		resultActions.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value(FavoriteErrorCode.LECTURE_NOT_FAVORITED.getMessage()));
	}

	private List<FindFavoriteResponse> createFavorites() {
		return List.of(
			new FindFavoriteResponse(328238L, "자바스크립트 : 기초부터 실전까지 올인원",
				"https://www.inflearn.com/course/자바스크립트-올인원",
				"https://cdn.inflearn.com/public/courses/328238/cover/f3821bcd-cac4-470a-b3bc-edc8d699a604/328238-eng.png",
				"코딩알려주는누나", 500L, 81400),
			new FindFavoriteResponse(332113L, "따라하며 배우는 자바스크립트 A-Z",
				"https://www.inflearn.com/course/따라하며-배우는-자바스크립트",
				"https://cdn.inflearn.com/public/courses/332113/cover/2e916cc7-0294-4db6-ae97-8acae5f1d512/332113-eng.png",
				"John Ahn", 200L, 55000)
		);
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