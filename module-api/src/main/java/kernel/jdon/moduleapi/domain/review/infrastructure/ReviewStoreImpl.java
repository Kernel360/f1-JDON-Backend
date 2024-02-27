package kernel.jdon.moduleapi.domain.review.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.review.core.ReviewStore;
import kernel.jdon.moduledomain.review.domain.Review;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewStoreImpl implements ReviewStore {
	private final ReviewRepository reviewRepository;

	@Override
	public Review save(final Review initReview) {
		return reviewRepository.save(initReview);
	}

}
