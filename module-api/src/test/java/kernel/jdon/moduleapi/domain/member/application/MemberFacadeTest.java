package kernel.jdon.moduleapi.domain.member.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.member.core.MemberInfo;
import kernel.jdon.moduleapi.domain.member.core.MemberService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member Facade 테스트")
class MemberFacadeTest {
	@Mock
	private MemberService memberService;
	@InjectMocks
	private MemberFacade memberFacade;

	@Test
	@DisplayName("1: 사용자 정보 요청 시, memberId에 해당되는 멤버의 정보를 응답으로 반환한다.")
	void getMember() {
		//given
		var memberId = 1L;
		var mockFindMemberResponse = mock(MemberInfo.FindMemberResponse.class);
		when(memberService.getMember(memberId)).thenReturn(mockFindMemberResponse);

		//when
		var response = memberFacade.getMember(memberId);

		//then
		assertThat(response).isEqualTo(mockFindMemberResponse);

		//verify
		verify(memberService, times(1)).getMember(memberId);
	}

}