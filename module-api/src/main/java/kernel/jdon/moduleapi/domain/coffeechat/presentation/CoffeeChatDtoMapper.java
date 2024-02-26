package kernel.jdon.moduleapi.domain.coffeechat.presentation;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatCommand;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CoffeeChatDtoMapper {
    CoffeeChatDto.FindCoffeeChatResponse of(CoffeeChatInfo.FindCoffeeChatResponse info);

    CoffeeChatCommand.CreateCoffeeChatRequest of(CoffeeChatDto.CreateCoffeeChatRequest request);

    CoffeeChatCommand.UpdateCoffeeChatRequest of(CoffeeChatDto.UpdateCoffeeChatRequest request);

    CoffeeChatCommand.FindCoffeeChatListRequest of(CoffeeChatCondition request);

    CoffeeChatDto.FindCoffeeChatListResponse of(CoffeeChatInfo.FindCoffeeChatListResponse info);
}
