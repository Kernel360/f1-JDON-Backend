package kernel.jdon.moduleapi.domain.member.core;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberReader memberReader;
	private final MemberInfoMapper memberInfoMapper;

	@Override
	public MemberInfo.FindMemberResponse find(final Long memberId) {
		final Member findMember = memberReader.findById(memberId);
		final List<Long> skillIdList = memberReader.findSkillIdListByMember(findMember);

		log.info(findMember.getNickname());
		return memberInfoMapper.of(findMember, skillIdList);
	}
}
