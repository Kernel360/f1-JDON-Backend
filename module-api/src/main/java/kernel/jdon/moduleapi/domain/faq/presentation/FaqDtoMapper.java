package kernel.jdon.moduleapi.domain.faq.presentation;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.moduleapi.domain.faq.core.FaqCommand;
import kernel.jdon.moduleapi.domain.faq.core.FaqInfo;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FaqDtoMapper {
    FaqCommand.CreateFaqRequest of(FaqDto.CreateFaqRequest request);

    FaqDto.CreateFaqResponse of(FaqInfo.CreateFaqResponse info);

    FaqCommand.UpdateFaqRequest of(FaqDto.UpdateFaqRequest request, Long faqId);

    FaqDto.UpdateFaqResponse of(FaqInfo.UpdateFaqResponse info);

    FaqDto.DeleteFaqResponse of(FaqInfo.DeleteFaqResponse info);

    FaqDto.FindFaqListResponse of(FaqInfo.FindFaqListResponse info);
}
