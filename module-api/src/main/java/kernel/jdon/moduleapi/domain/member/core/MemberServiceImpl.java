package kernel.jdon.moduleapi.domain.member.core;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.auth.util.CryptoManager;
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
	private final MemberStore memberStore;
	private final MemberInfoMapper memberInfoMapper;
	private final MemberFactory memberFactory;
	private final CryptoManager cryptoManager;

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
	public void checkNicknameDuplicate(final MemberCommand.NicknameDuplicateRequest command) {
		final boolean isExistNickname = memberReader.existsByNickname(command.getNickname());
		if (isExistNickname) {
			throw new ApiException(MemberErrorCode.CONFLICT_DUPLICATE_NICKNAME);
		}
	}

	@Override
	@Transactional
	public MemberInfo.RegisterResponse register(final MemberCommand.RegisterRequest command) {
		final Map<String, String> userInfo = cryptoManager.getUserInfoFromAuthProvider(command.getHmac(),
			command.getEncrypted());
		final Member savedMember = memberFactory.save(command, userInfo);

		return MemberInfo.RegisterResponse.of(savedMember.getId());
	}

	@Override
	@Transactional
	public MemberInfo.WithdrawResponse removeMember(final MemberCommand.WithdrawRequest command) {
		memberStore.updateAccountStatusWithdrawById(command.getId());

		return MemberInfo.WithdrawResponse.of(command.getId());
	}
}
