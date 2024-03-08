package kernel.jdon.moduleapi.domain.jd.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.jd.core.JdInfo;
import kernel.jdon.moduleapi.domain.jd.core.JdService;
import kernel.jdon.moduleapi.domain.jd.presentation.JdCondition;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;

@DisplayName("JD Facade 테스트")
@ExtendWith(MockitoExtension.class)
class JdFacadeTest {
	@InjectMocks
	private JdFacade jdFacade;
	@Mock
	private JdService jdService;

	@Test
	@DisplayName("1: jdId가 주어졌을 때 getJd 메서드가 jd 상세정보를 반환한다.")
	void givenValidJdId_whenGetJd_thenReturnCorrectJd() throws Exception {
		//given
		final var jdId = 1L;
		final var mockFindWantedJdInfo = mock(JdInfo.FindWantedJdResponse.class);
		given(jdService.getJd(jdId)).willReturn(mockFindWantedJdInfo);

		//when
		final var response = jdFacade.getJd(jdId);

		//then
		assertThat(response).isEqualTo(mockFindWantedJdInfo);
		then(jdService).should(times(1)).getJd(jdId);
	}

	@Test
	@DisplayName("2: 올바른 JdCondition이 주어졌을 때 getJdList가 jd 목록을 반환한다.")
	void givenValidJdCondition_whenGetJdList_thenReturnCorrectJdList() throws Exception {
		//given
		final var mockPageInfoRequest = mock(PageInfoRequest.class);
		final var mockJdCondition = mock(JdCondition.class);
		final var mockFindWantedJdListInfo = mock(JdInfo.FindWantedJdListResponse.class);
		given(jdService.getJdList(mockPageInfoRequest, mockJdCondition)).willReturn(mockFindWantedJdListInfo);

		//when
		final var response = jdFacade.getJdList(mockPageInfoRequest, mockJdCondition);

		//then
		assertThat(response).isEqualTo(mockFindWantedJdListInfo);
		then(jdService).should(times(1)).getJdList(mockPageInfoRequest, mockJdCondition);
	}
}