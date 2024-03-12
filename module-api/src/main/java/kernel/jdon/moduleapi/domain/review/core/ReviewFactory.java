package kernel.jdon.moduleapi.domain.review.core;

import kernel.jdon.moduledomain.review.domain.Review;

public interface ReviewFactory {
    Review saveReview(ReviewCommand.CreateReviewRequest command);
}
