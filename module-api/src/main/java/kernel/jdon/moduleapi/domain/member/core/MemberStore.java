package kernel.jdon.moduleapi.domain.member.core;

import kernel.jdon.member.domain.Member;

public interface MemberStore {
	void update(Member target, Member updateMember);

	Member save(Member saveMember);

	void deleteById(Long memberId);

	void updateLastLoginDate(Member member);
}
