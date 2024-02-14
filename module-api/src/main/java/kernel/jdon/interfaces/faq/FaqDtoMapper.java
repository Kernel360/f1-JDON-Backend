package kernel.jdon.interfaces.faq;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.domain.faq.FaqCommand;
import kernel.jdon.domain.faq.FaqInfo;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FaqDtoMapper {
	FaqCommand.CreateRequest of(FaqDto.CreateRequest request);
	FaqDto.CreateResponse of(FaqInfo.CreateResponse info);
	FaqCommand.UpdateRequest of (FaqDto.UpdateRequest request);
	FaqDto.UpdateResponse of (FaqInfo.UpdateResponse info);
	FaqDto.DeleteResponse of (FaqInfo.DeleteResponse info);
	FaqDto.FindListResponse of(FaqInfo.FindListResponse info);
}
