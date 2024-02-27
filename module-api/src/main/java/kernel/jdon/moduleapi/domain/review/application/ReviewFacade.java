package kernel.jdon.moduleapi.domain.review.application;

import org.springframework.stereotype.Service;

import kernel.jdon.moduleapi.domain.review.core.ReviewCommand;
import kernel.jdon.moduleapi.domain.review.core.ReviewInfo;
import kernel.jdon.moduleapi.domain.review.core.ReviewService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewFacade {
	private final ReviewService reviewService;

	public ReviewInfo.CreateReviewResponse createReview(final ReviewCommand.CreateReviewRequest command) {
		return reviewService.createReview(command);
	}
}
