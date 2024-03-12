package kernel.jdon.moduleapi.domain.review.core;

import kernel.jdon.moduledomain.review.domain.Review;

public interface ReviewStore {
    Review save(Review initReview);

    void delete(Review review);
}
