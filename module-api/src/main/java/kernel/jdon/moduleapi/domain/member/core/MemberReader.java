package kernel.jdon.moduleapi.domain.member.core;

import java.util.List;

import kernel.jdon.member.domain.Member;

public interface MemberReader {
	Member findById(Long memberId);

	boolean existsById(Long memberId);

	List<Long> findSkillIdListByMember(Member findMember);

	boolean existsByNickname(String nickname);
}
