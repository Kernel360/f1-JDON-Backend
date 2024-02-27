package kernel.jdon.moduleapi.domain.review.core;

public interface ReviewService {
	ReviewInfo.CreateReviewResponse createReview(ReviewCommand.CreateReviewRequest command);
}
