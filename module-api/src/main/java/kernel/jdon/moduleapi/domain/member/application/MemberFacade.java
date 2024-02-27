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

	public MemberInfo.FindMemberResponse getMember(Long memberId) {
		return memberService.getMember(memberId);
	}

	public MemberInfo.UpdateMemberResponse modifyMember(Long memberId, MemberCommand.UpdateMemberRequest command) {
		return memberService.modifyMember(memberId, command);
	}
}
