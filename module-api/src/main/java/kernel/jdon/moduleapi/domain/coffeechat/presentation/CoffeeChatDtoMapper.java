package kernel.jdon.moduleapi.domain.coffeechat.presentation;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CoffeeChatDtoMapper {
	CoffeeChatDto.FindResponse of(CoffeeChatInfo.FindResponse info);
}
