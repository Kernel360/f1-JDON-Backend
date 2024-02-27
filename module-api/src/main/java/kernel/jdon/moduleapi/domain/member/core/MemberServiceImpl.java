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
	private final MemberStore memberStore;
	private final MemberInfoMapper memberInfoMapper;
	private final MemberFactory memberFactory;

	@Override
	public MemberInfo.FindMemberResponse getMember(final Long memberId) {
		final Member findMember = memberReader.findById(memberId);
		final List<Long> skillIdList = memberReader.findSkillIdListByMember(findMember);

		return memberInfoMapper.of(findMember, skillIdList);
	}

	@Override
	@Transactional
	public MemberInfo.UpdateMemberResponse modifyMember(Long memberId, MemberCommand.UpdateMemberRequest command) {
		Member findMember = memberReader.findById(memberId);
		Member updateMember = memberFactory.toUpdateMember(findMember, command);
		final Member updatedMember = memberStore.update(findMember, updateMember);

		return MemberInfo.UpdateMemberResponse.of(updatedMember.getId());
	}
}
