package kernel.jdon.moduleapi.domain.review.infrastructure;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.moduleapi.domain.review.core.ReviewInfo;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ReviewReaderInfoMapper {
    ReviewInfo.FindReview of(ReviewReaderInfo.FindReview readerInfo);
}
