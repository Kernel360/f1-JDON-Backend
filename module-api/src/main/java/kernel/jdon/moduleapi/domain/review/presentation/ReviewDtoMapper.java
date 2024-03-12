package kernel.jdon.moduleapi.domain.review.presentation;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.moduleapi.domain.review.core.ReviewCommand;
import kernel.jdon.moduleapi.domain.review.core.ReviewInfo;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ReviewDtoMapper {
    ReviewCommand.CreateReviewRequest of(ReviewDto.CreateReviewRequest request, Long memberId);

    ReviewDto.CreateReviewResponse of(ReviewInfo.CreateReviewResponse info);

    ReviewDto.FindReviewListResponse of(ReviewInfo.FindReviewListResponse info);

    ReviewCommand.DeleteReviewRequest of(Long reviewId, Long memberId);
}
