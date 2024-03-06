package kernel.jdon.moduleapi.domain.jd.core;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.jd.presentation.JdCondition;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;

@DisplayName("Jd Service Impl 테스트")
@ExtendWith(MockitoExtension.class)
class JdServiceImplTest {
	@InjectMocks
	private JdServiceImpl jdServiceImpl;
	@Mock
	private JdReader jdReader;
	@Mock
	private JdInfoMapper jdInfoMapper;

	@Test
	@DisplayName("1: jdId가 주어졌을 때 getJd 메서드가 jd 상세정보와 스킬목록을 반환한다.")
	void givenValidJdId_whenGetJd_thenReturnCorrectJd() throws Exception {
		//given
		final var jdId = 1L;
		final var mockWantedJd = mock(WantedJd.class);
		final var mockSkillList = List.of(
			mock(JdInfo.FindSkill.class),
			mock(JdInfo.FindSkill.class));
		final var mockFindWantedInfo = mockFindWantedInfo(jdId, mockSkillList);
		given(jdReader.findWantedJd(jdId))
			.willReturn(mockWantedJd);
		given(jdReader.findSkillListByWantedJd(mockWantedJd))
			.willReturn(mockSkillList);
		given(jdInfoMapper.of(mockWantedJd, mockSkillList))
			.willReturn(mockFindWantedInfo);

		//when
		final var response = jdServiceImpl.getJd(jdId);

		//then
		assertThat(response.getSkillList()).isEqualTo(mockSkillList);
		then(jdReader).should(times(1))
			.findWantedJd(jdId);
		then(jdReader).should(times(1))
			.findSkillListByWantedJd(mockWantedJd);
	}

	private JdInfo.FindWantedJdResponse mockFindWantedInfo(final Long jdId, final List<JdInfo.FindSkill> skillList) {
		return JdInfo.FindWantedJdResponse.builder()
			.id(jdId)
			.skillList(skillList)
			.build();
	}

	@Test
	@DisplayName("2: JdCondition이 주어졌을 때 getJdList가 jd 목록을 반환한다.")
	void givenValidJdCondition_whenGetJdList_thenReturnCorrectJdList() throws Exception {
		//given
		final var mockPageInfoRequest = mock(PageInfoRequest.class);
		final var mockJdCondition = mock(JdCondition.class);
		final var mockFindWantedJdListInfo = mock(JdInfo.FindWantedJdListResponse.class);
		given(jdReader.findWantedJdList(mockPageInfoRequest, mockJdCondition))
			.willReturn(mockFindWantedJdListInfo);

		//when
		final var response = jdServiceImpl.getJdList(mockPageInfoRequest, mockJdCondition);

		//then
		assertThat(response).isEqualTo(mockFindWantedJdListInfo);
		then(jdReader).should(times(1))
			.findWantedJdList(mockPageInfoRequest, mockJdCondition);
	}
}