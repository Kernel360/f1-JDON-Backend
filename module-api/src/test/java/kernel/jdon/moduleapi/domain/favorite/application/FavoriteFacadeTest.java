package kernel.jdon.moduleapi.domain.favorite.application;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.favorite.core.FavoriteCommand;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfo;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteService;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;

@DisplayName("Favorite Facade 테스트")
@ExtendWith(MockitoExtension.class)
class FavoriteFacadeTest {

	@Mock
	private FavoriteService favoriteService;

	@InjectMocks
	private FavoriteFacade favoriteFacade;

	@DisplayName("유효한 요청이 주어졌을 때, 찜하기를 하면 favorite id를 응답한다.")
	@Test
	void givenValidRequest_whenCreateFavorite_thenReturnCreatedFavorite() {
		// given
		Long memberId = 1L;
		FavoriteCommand.UpdateRequest request = new FavoriteCommand.UpdateRequest(2L, true);
		FavoriteInfo.UpdateResponse expectedResponse = new FavoriteInfo.UpdateResponse(2L);

		// when
		when(favoriteService.save(memberId, request.getLectureId())).thenReturn(expectedResponse);
		FavoriteInfo.UpdateResponse actualResponse = favoriteFacade.update(memberId, request);

		// then
		assertThat(expectedResponse.getLectureId()).isEqualTo(actualResponse.getLectureId());
		verify(favoriteService, times(1)).save(memberId, request.getLectureId());
	}

	@DisplayName("유효한 요청으로 즐겨찾기 삭제하면, 삭제된 favorite id를 응답한다.")
	@Test
	void givenValidRequest_whenDeleteFavorite_thenReturnDeletedFavorite() {
		// given
		Long memberId = 1L;
		Long lectureId = 2L;
		FavoriteCommand.UpdateRequest request = new FavoriteCommand.UpdateRequest(2L, false);
		FavoriteInfo.UpdateResponse expectedResponse = new FavoriteInfo.UpdateResponse(lectureId);

		// when
		when(favoriteService.delete(memberId, lectureId)).thenReturn(expectedResponse);
		FavoriteInfo.UpdateResponse actualResponse = favoriteFacade.update(memberId, request);

		// then
		assertThat(actualResponse).isEqualTo(expectedResponse);
		verify(favoriteService, times(1)).delete(memberId, lectureId);
	}

	@DisplayName("내가 찜한 목록을 요청하면, 찜한 목록을 응답한다.")
	@Test
	void whenGetList_thenReturnList() {
		// given
		Long memberId = 1L;
		PageInfoRequest pageInfoRequest = new PageInfoRequest(0, 10);
		FavoriteInfo.FindFavoriteListResponse expectedResponse = mock(FavoriteInfo.FindFavoriteListResponse.class);

		// when
		when(favoriteService.getList(memberId, pageInfoRequest)).thenReturn(expectedResponse);
		FavoriteInfo.FindFavoriteListResponse actualResponse = favoriteFacade.getList(memberId, pageInfoRequest);

		// then
		assertThat(expectedResponse).isEqualTo(actualResponse);
		verify(favoriteService, times(1)).getList(memberId, pageInfoRequest);
	}

}