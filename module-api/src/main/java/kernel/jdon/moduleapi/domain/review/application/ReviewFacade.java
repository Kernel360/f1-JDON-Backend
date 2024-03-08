package kernel.jdon.moduleapi.domain.review.application;

import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.review.core.ReviewCommand;
import kernel.jdon.moduleapi.domain.review.core.ReviewInfo;
import kernel.jdon.moduleapi.domain.review.core.ReviewService;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewFacade {
	private final ReviewService reviewService;

	public ReviewInfo.CreateReviewResponse createReview(final ReviewCommand.CreateReviewRequest command) {
		return reviewService.createReview(command);
	}

	public ReviewInfo.FindReviewListResponse getReviewList(final Long jdId, final PageInfoRequest pageInfoRequest,
		final Long reviewId) {
		return reviewService.getReviewList(jdId, pageInfoRequest, reviewId);
	}

	public void removeReview(final ReviewCommand.DeleteReviewRequest command) {
		reviewService.removeReview(command);
	}
}
