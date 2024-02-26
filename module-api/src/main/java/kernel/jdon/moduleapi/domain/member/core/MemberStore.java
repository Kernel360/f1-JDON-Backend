package kernel.jdon.moduleapi.domain.member.core;

import kernel.jdon.member.domain.Member;

public interface MemberStore {
	Member update(Member target, Member updateMember);
}
