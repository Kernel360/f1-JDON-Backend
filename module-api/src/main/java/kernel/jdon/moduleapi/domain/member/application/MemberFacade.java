package kernel.jdon.moduleapi.domain.member.application;

import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.member.core.MemberCommand;
import kernel.jdon.moduleapi.domain.member.core.MemberInfo;
import kernel.jdon.moduleapi.domain.member.core.MemberService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFacade {
	private final MemberService memberService;

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
}
