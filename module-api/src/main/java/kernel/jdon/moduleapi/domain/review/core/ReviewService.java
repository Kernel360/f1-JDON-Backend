package kernel.jdon.moduleapi.domain.review.core;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;

public interface ReviewService {
    ReviewInfo.CreateReviewResponse createReview(ReviewCommand.CreateReviewRequest command);

    ReviewInfo.FindReviewListResponse getReviewList(Long jdId, PageInfoRequest pageInfoRequest, Long reviewId);

    void removeReview(ReviewCommand.DeleteReviewRequest command);
}
