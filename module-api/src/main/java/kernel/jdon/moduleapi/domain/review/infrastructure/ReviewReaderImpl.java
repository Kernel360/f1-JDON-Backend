package kernel.jdon.moduleapi.domain.review.infrastructure;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.review.core.ReviewInfo;
import kernel.jdon.moduleapi.domain.review.core.ReviewReader;
import kernel.jdon.moduleapi.domain.review.error.ReviewErrorCode;
import kernel.jdon.moduleapi.global.page.CustomJpaPageInfo;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.review.domain.Review;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ReviewReaderImpl implements ReviewReader {
	private final ReviewRepository reviewRepository;
	private final ReviewReaderInfoMapper reviewReaderInfoMapper;

	@Override
	public ReviewInfo.FindReviewListResponse findReviewList(final Long jdId, final PageInfoRequest pageInfoRequest) {
		final Pageable pageable = PageRequest.of(pageInfoRequest.getPage(), pageInfoRequest.getSize());

		final Page<ReviewReaderInfo.FindReview> findReviewList = reviewRepository.findReviewList(jdId, pageable);

		final List<ReviewInfo.FindReview> content = findReviewList.stream()
			.map(reviewReaderInfoMapper::of)
			.toList();

		return new ReviewInfo.FindReviewListResponse(content, new CustomJpaPageInfo(findReviewList));
	}

	@Override
	public Review findById(final Long id) {
		return reviewRepository.findById(id)
			.orElseThrow(ReviewErrorCode.NOT_FOUND_REVIEW::throwException);
	}
}
