package kernel.jdon.moduleapi.domain.review.core;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.review.domain.Review;

public interface ReviewReader {
	ReviewInfo.FindReviewListResponse findReviewList(Long jdId, PageInfoRequest pageInfoRequest);

	Review findById(Long id);
}
