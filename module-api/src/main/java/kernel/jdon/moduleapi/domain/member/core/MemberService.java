package kernel.jdon.moduleapi.domain.member.core;

import kernel.jdon.moduleapi.domain.auth.dto.SessionUserInfo;

public interface MemberService {
	MemberInfo.FindMemberResponse getMember(Long memberId);

	MemberInfo.UpdateMemberResponse modifyMember(Long memberId, MemberCommand.UpdateMemberRequest command);

	void checkNicknameDuplicate(MemberCommand.NicknameDuplicateRequest command);

	MemberInfo.RegisterResponse register(MemberCommand.RegisterRequest command);

	MemberInfo.WithdrawResponse removeMember(SessionUserInfo userInfo);
}
