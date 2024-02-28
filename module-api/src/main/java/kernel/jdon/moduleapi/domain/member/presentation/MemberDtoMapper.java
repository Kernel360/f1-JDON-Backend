package kernel.jdon.moduleapi.domain.member.presentation;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.moduleapi.domain.member.core.MemberCommand;
import kernel.jdon.moduleapi.domain.member.core.MemberInfo;
import kernel.jdon.moduleapi.global.dto.SessionUserInfo;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberDtoMapper {

	MemberDto.FindMemberResponse of(MemberInfo.FindMemberResponse info);

	MemberCommand.UpdateMemberRequest of(MemberDto.UpdateMemberRequest request);

	MemberDto.UpdateMemberResponse of(MemberInfo.UpdateMemberResponse response);

	MemberCommand.NicknameDuplicateRequest of(MemberDto.NicknameDuplicateRequest request);

	MemberCommand.RegisterRequest of(MemberDto.RegisterRequest request);

	MemberDto.RegisterResponse of(MemberInfo.RegisterResponse response);

	MemberDto.WithdrawResponse of(MemberInfo.WithdrawResponse response);

	MemberCommand.WithdrawRequest of(SessionUserInfo member);

}
