package kernel.jdon.moduleapi.domain.coffeechat.infrastructure;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface CoffeeChatReaderMapper {
    // Page<CoffeeChatInfo.FindCoffeeChatListResponse> of(
    // 	Page<CoffeeChatReaderInfo.FindCoffeeChatListResponse> readerInfo);
}
