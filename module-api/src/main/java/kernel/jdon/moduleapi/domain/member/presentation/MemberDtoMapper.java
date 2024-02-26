package kernel.jdon.moduleapi.domain.member.presentation;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.moduleapi.domain.member.core.MemberInfo;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberDtoMapper {
	MemberDto.FindMemberResponse of(MemberInfo.FindMemberResponse info);
}
