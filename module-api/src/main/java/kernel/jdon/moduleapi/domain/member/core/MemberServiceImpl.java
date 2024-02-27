package kernel.jdon.moduleapi.domain.member.core;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import kernel.jdon.moduleapi.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
	private final MemberReader memberReader;
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
	public MemberInfo.UpdateMemberResponse modifyMember(final Long memberId,
		final MemberCommand.UpdateMemberRequest command) {
		final Member findMember = memberReader.findById(memberId);
		memberFactory.update(findMember, command);

		return MemberInfo.UpdateMemberResponse.of(findMember.getId());
	}

	@Override
	public void checkNicknameDuplicate(MemberCommand.NicknameDuplicateRequest command) {
		final boolean isExistNickname = memberReader.existsByNickname(command.getNickname());
		if (isExistNickname) {
			throw new ApiException(MemberErrorCode.CONFLICT_DUPLICATE_NICKNAME);
		}
	}
}
