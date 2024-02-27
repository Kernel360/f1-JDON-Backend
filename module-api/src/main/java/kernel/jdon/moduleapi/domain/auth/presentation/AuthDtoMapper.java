package kernel.jdon.moduleapi.domain.auth.presentation;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.moduleapi.domain.auth.core.AuthCommand;
import kernel.jdon.moduleapi.domain.auth.core.AuthInfo;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AuthDtoMapper {

	AuthCommand.RegisterRequest of(AuthDto.RegisterRequest request);

	AuthDto.RegisterResponse of(AuthInfo.RegisterResponse response);
}
