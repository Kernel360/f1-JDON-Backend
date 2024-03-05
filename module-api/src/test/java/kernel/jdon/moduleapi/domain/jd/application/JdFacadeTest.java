package kernel.jdon.moduleapi.domain.jd.application;

import static org.mockito.Mockito.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.jd.core.JdInfo;
import kernel.jdon.moduleapi.domain.jd.core.JdService;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;

@DisplayName("JD Facade 테스트")
@ExtendWith(MockitoExtension.class)
class JdFacadeTest {
	@InjectMocks
	private JdFacade jdFacade;
	@Mock
	private JdService jdService;

	@Test
	@DisplayName("1: 올바른 jdId가 주어졌을 때 getJd 메서드가 jd 상세정보와 스킬목록을 반환한다.")
	void givenValidJdId_whenGetJd_thenReturnCorrectJd() throws Exception {
		//given
		final var jdId = 1L;
		final var mockFindWantedJdResponse = mock(JdInfo.FindWantedJdResponse.class);

		//when
		when(jdService.getJd(jdId)).thenReturn(mockFindWantedJdResponse);
		final var response = jdFacade.getJd(jdId);

		//then
		Assertions.assertThat(response).isNotNull();
		verify(jdService, times(1)).getJd(jdId);
	}

	@Test
	@DisplayName("2: 올바른 keyword가 주어졌을 때 getJdList가 jd 목록을 반환한다.")
	void givenValidKeyword_whenGetJdList_thenReturnCorrectJdList() throws Exception {
		//given
		final var keyword = "keyword";
		final var mockPageInfoRequest = mock(PageInfoRequest.class);
		final var mockFindWantedJdListResponse = mock(JdInfo.FindWantedJdListResponse.class);

		//when
		when(jdService.getJdList(mockPageInfoRequest, keyword)).thenReturn(mockFindWantedJdListResponse);
		final var response = jdFacade.getJdList(mockPageInfoRequest, keyword);

		//then
		Assertions.assertThat(response).isNotNull();
		verify(jdService, times(1)).getJdList(mockPageInfoRequest, keyword);
	}
}