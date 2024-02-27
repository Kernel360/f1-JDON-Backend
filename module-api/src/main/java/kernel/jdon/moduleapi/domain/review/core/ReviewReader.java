package kernel.jdon.moduleapi.domain.review.core;

import kernel.jdon.moduleapi.global.page.PageInfoRequest;

public interface ReviewReader {
	ReviewInfo.FindReviewListResponse findReviewList(Long jdId, PageInfoRequest pageInfoRequest);
}
