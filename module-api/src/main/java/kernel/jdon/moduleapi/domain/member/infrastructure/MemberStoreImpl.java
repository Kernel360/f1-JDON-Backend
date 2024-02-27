package kernel.jdon.moduleapi.domain.member.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.member.core.MemberStore;
import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import kernel.jdon.moduleapi.global.exception.ApiException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberStoreImpl implements MemberStore {
	private final MemberRepository memberRepository;

	@Override
	public void update(final Member target, final Member updateMember) {
		target.update(updateMember);
	}

	@Override
	public Member save(Member saveMember) {
		return memberRepository.save(saveMember);
	}

	@Override
	public void deleteById(final Long memberId) {
		memberRepository.findById(memberId)
			.orElseThrow(() -> new ApiException(MemberErrorCode.NOT_FOUND_MEMBER))
			.withdrawMemberAccount();
	}
}
