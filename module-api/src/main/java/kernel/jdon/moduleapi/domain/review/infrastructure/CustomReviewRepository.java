package kernel.jdon.moduleapi.domain.review.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomReviewRepository {
	Page<ReviewReaderInfo.FindReview> findReviewList(Long jdId, Pageable pageable);
}
