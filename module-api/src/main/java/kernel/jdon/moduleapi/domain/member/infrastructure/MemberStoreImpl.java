package kernel.jdon.moduleapi.domain.member.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.member.core.MemberStore;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberStoreImpl implements MemberStore {
	private final MemberRepository memberRepository;

	@Override
	public Member update(Member target, Member updateMember) {
		return target.update(updateMember);
	}
}
