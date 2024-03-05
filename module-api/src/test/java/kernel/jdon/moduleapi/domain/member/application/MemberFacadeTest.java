package kernel.jdon.moduleapi.domain.member.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import kernel.jdon.moduleapi.domain.member.core.MemberCommand;
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
	@DisplayName("1: 사용자 정보 요청 시, get 메서드가 memberId에 해당되는 멤버의 정보를 응답으로 반환한다.")
	void whenGetMemberInfo_thenReturnMemberInfo() {
		//given
		final var memberId = 1L;
		final var mockFindMemberResponse = mock(MemberInfo.FindMemberResponse.class);

		//when
		when(memberService.getMember(memberId)).thenReturn(mockFindMemberResponse);
		final var response = memberFacade.getMember(memberId);

		//then
		assertThat(response).isEqualTo(mockFindMemberResponse);

		//verify
		verify(memberService, times(1)).getMember(memberId);
	}

	@Test
	@DisplayName("2: 사용자 아이디와 정보 수정 정보가 주어졌을 때, modify 메서드가 수정을 마친 memberId를 응답으로 반환한다.")
	void givenMemberIdAndMemberModifyInfo_whenModifyMember_thenReturnModifiedMemberId() {
		//given
		final var modifyMemberId = 1L;
		final var mockModifyMemberCommand = mock(MemberCommand.UpdateMemberRequest.class);
		final var modifyMemberResponse = MemberInfo.UpdateMemberResponse.of(modifyMemberId);

		//when
		when(memberService.modifyMember(modifyMemberId, mockModifyMemberCommand)).thenReturn(modifyMemberResponse);
		final var response = memberFacade.modifyMember(modifyMemberId, mockModifyMemberCommand);

		//then
		assertThat(response.getMemberId()).isEqualTo(modifyMemberId);

		//verify
		verify(memberService, times(1)).modifyMember(modifyMemberId, mockModifyMemberCommand);
	}

	@Test
	@DisplayName("3: 사용자 아이디와 정보 수정 정보가 주어졌을 때, modify 메서드가 수정을 마친 memberId를 응답으로 반환한다.")
	void givenNickname_whenCheckNicknameDuplicate() {
		//given
		final var mockCommand = mock(MemberCommand.NicknameDuplicateRequest.class);

		//when
		doNothing().when(memberService).checkNicknameDuplicate(any(MemberCommand.NicknameDuplicateRequest.class));
		memberFacade.checkNicknameDuplicate(mockCommand);

		//verify
		verify(memberService, times(1)).checkNicknameDuplicate(any(MemberCommand.NicknameDuplicateRequest.class));
	}

	@Test
	@DisplayName("4: 사용자 등록 정보가 주어졌을 때, register 메서드가 등록을 마친 memberId를 응답으로 반환한다.")
	void givenRegisterInfo_whenRegister_thenReturnMemberId() {
		//given
		final var mockCommand = mock(MemberCommand.RegisterRequest.class);
		final var mockResponse = MemberInfo.RegisterResponse.of(1L);

		//when
		when(memberService.register(mockCommand)).thenReturn(mockResponse);
		final var response = memberFacade.register(mockCommand);

		//then
		assertThat(response).isEqualTo(mockResponse);
		assertThat(response.getMemberId()).isEqualTo(mockResponse.getMemberId());

		//verify
		verify(memberService, times(1)).register(mockCommand);
	}
}