package kernel.jdon.moduleapi.domain.member.infrastructure;

import java.util.List;

import org.springframework.stereotype.Component;

import kernel.jdon.moduledomain.member.domain.Member;
import kernel.jdon.moduleapi.domain.member.core.MemberReader;
import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberReaderImpl implements MemberReader {

	private final MemberRepository memberRepository;

	@Override
	public Member findById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(MemberErrorCode.NOT_FOUND_MEMBER::throwException);
	}

	@Override
	public boolean existsById(Long memberId) {
		return memberRepository.existsById(memberId);
	}

	@Override
	public List<Long> findSkillIdListByMember(final Member member) {
		return member.getMemberSkillList().stream()
			.map(memberSkill -> memberSkill.getSkill().getId())
			.toList();
	}

	@Override
	public boolean existsByNickname(final String nickname) {
		return memberRepository.existsByNickname(nickname);
	}

	@Override
	public Member findByEmail(final String email) {
		return memberRepository.findByEmail(email);
	}
}