package kernel.jdon.moduleapi.domain.member.application;

import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.auth.core.CustomOAuth2UserServiceImpl;
import kernel.jdon.moduleapi.domain.auth.error.AuthErrorCode;
import kernel.jdon.moduleapi.domain.member.core.MemberCommand;
import kernel.jdon.moduleapi.domain.member.core.MemberInfo;
import kernel.jdon.moduleapi.domain.member.core.MemberService;
import kernel.jdon.moduleapi.global.dto.SessionUserInfo;
import kernel.jdon.moduleapi.global.exception.ApiException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFacade {
	private final MemberService memberService;
	private final CustomOAuth2UserServiceImpl customOAuth2UserServiceImpl;

	public MemberInfo.FindMemberResponse getMember(final Long memberId) {
		return memberService.getMember(memberId);
	}

	public MemberInfo.UpdateMemberResponse modifyMember(final Long memberId,
		final MemberCommand.UpdateMemberRequest command) {

		return memberService.modifyMember(memberId, command);
	}

	public void checkNicknameDuplicate(final MemberCommand.NicknameDuplicateRequest command) {
		memberService.checkNicknameDuplicate(command);
	}

	public MemberInfo.RegisterResponse register(final MemberCommand.RegisterRequest command) {
		return memberService.register(command);
	}

	public MemberInfo.WithdrawResponse withdrawMember(final SessionUserInfo userInfo) {
		if (!customOAuth2UserServiceImpl.sendDeleteRequestToOAuth2(userInfo)) {
			throw new ApiException(AuthErrorCode.ERROR_FAIL_TO_UNLINK_OAUTH2);
		}
		return memberService.removeMember(userInfo);
	}
}