package kernel.jdon.moduleapi.domain.member.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import kernel.jdon.moduleapi.domain.auth.core.CustomOAuth2UserService;
import kernel.jdon.moduleapi.domain.auth.error.AuthErrorCode;
import kernel.jdon.moduleapi.domain.member.core.MemberCommand;
import kernel.jdon.moduleapi.domain.member.core.MemberInfo;
import kernel.jdon.moduleapi.domain.member.core.MemberService;
import kernel.jdon.moduleapi.global.exception.ApiException;

@ExtendWith(MockitoExtension.class)
@DisplayName("Member Facade 테스트")
class MemberFacadeTest {
	@Mock
	private MemberService memberService;

	@Mock
	private CustomOAuth2UserService customOAuth2UserService;

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
	@DisplayName("3: 닉네임이 주어졌을 때, checkNicknameDuplicate 메서드가 service의 checkNicknameDuplicate를 실행한다.")
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

	@Test
	@DisplayName("5: 사용자 탈퇴 정보가 주어졌을 때, withdraw 메서드가 탈퇴 성공하면 탈퇴한 memberId를 응답으로 반환한다.")
	void givenWithdrawInfo_whenWithdraw_thenReturnWithdrawMemberId() {
		//given
		final var mockWithdrawCommand = mockWithdrawCommand();
		final var mockWithdrawResponse = MemberInfo.WithdrawResponse.of(mockWithdrawCommand.getId());

		//when
		when(customOAuth2UserService.sendDeleteRequestToOAuth2(mockWithdrawCommand)).thenReturn(true);
		when(memberService.removeMember(mockWithdrawCommand)).thenReturn(mockWithdrawResponse);
		final var response = memberFacade.withdrawMember(mockWithdrawCommand);

		//then
		assertThat(response).isEqualTo(mockWithdrawResponse);
		assertThat(response.getMemberId()).isEqualTo(mockWithdrawResponse.getMemberId());

		//verify
		verify(customOAuth2UserService, times(1)).sendDeleteRequestToOAuth2(mockWithdrawCommand);
		verify(memberService, times(1)).removeMember(mockWithdrawCommand);
	}

	@Test
	@DisplayName("6: 사용자 탈퇴 정보가 주어졌을 때, withdraw 메서드가 탈퇴 실패하면 500 에러를 던진다.")
	void givenWithdrawInfo_whenWithdraw_thenThrowException() {
		//given
		final var mockWithdrawCommand = mockWithdrawCommand();

		//when
		when(customOAuth2UserService.sendDeleteRequestToOAuth2(mockWithdrawCommand)).thenReturn(false);
		final var thrownException = assertThrows(ApiException.class,
			() -> memberFacade.withdrawMember(mockWithdrawCommand));

		// then
		assertThat(thrownException.getErrorCode().getHttpStatus().value())
			.isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
		assertThat(thrownException.getErrorCode().getMessage())
			.isEqualTo(AuthErrorCode.ERROR_FAIL_TO_UNLINK_OAUTH2.getMessage());

		//verify
		verify(customOAuth2UserService, times(1)).sendDeleteRequestToOAuth2(mockWithdrawCommand);
		verify(memberService, times(0)).removeMember(mockWithdrawCommand);
	}

	private MemberCommand.WithdrawRequest mockWithdrawCommand() {
		return MemberCommand.WithdrawRequest.builder()
			.id(1L)
			.build();
	}
}