package kernel.jdon.moduleapi.domain.member.application;

import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.member.core.MemberInfo;
import kernel.jdon.moduleapi.domain.member.core.MemberService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberFacade {
	private final MemberService memberService;

	public MemberInfo.FindMemberResponse find(Long memberId) {
		return memberService.find(memberId);
	}
}
