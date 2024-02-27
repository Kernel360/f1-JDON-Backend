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
	public void update(final Member target, final Member updateMember) {
		target.update(updateMember);
	}
}
