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

    CoffeeChatDto.CancelCoffeeChatResponse of(CoffeeChatInfo.CancelCoffeeChatResponse info);

    CoffeeChatDto.UpdateCoffeeChatResponse of(CoffeeChatInfo.UpdateCoffeeChatResponse info);

    CoffeeChatDto.CreateCoffeeChatResponse of(CoffeeChatInfo.CreateCoffeeChatResponse info);

    CoffeeChatDto.ApplyCoffeeChatResponse of(CoffeeChatInfo.ApplyCoffeeChatResponse info);

    CoffeeChatDto.DeleteCoffeeChatResponse of(CoffeeChatInfo.DeleteCoffeeChatResponse info);

    CoffeeChatDto.FindCoffeeChatResponse of(CoffeeChatInfo.FindCoffeeChatResponse info);

    CoffeeChatCommand.CreateCoffeeChatRequest of(CoffeeChatDto.CreateCoffeeChatRequest request);

    CoffeeChatCommand.UpdateCoffeeChatRequest of(CoffeeChatDto.UpdateCoffeeChatRequest request, Long coffeeChatId,
        Long memberId);

    CoffeeChatCommand.FindCoffeeChatListRequest of(CoffeeChatCondition request);

    CoffeeChatDto.FindCoffeeChatListResponse of(CoffeeChatInfo.FindCoffeeChatListResponse info);
}
