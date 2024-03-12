package kernel.jdon.moduleapi.domain.review.infrastructure;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CustomReviewRepository {
    Slice<ReviewReaderInfo.FindReview> findReviewList(Long jdId, Pageable pageable, Long reviewId);
}
