package kernel.jdon.moduleapi.domain.review.core;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.jd.core.JdReader;
import kernel.jdon.moduleapi.domain.member.core.MemberReader;
import kernel.jdon.moduleapi.domain.review.error.ReviewErrorCode;
import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.review.domain.Review;
import kernel.jdon.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewStore reviewStore;
	private final ReviewReader reviewReader;
	private final JdReader jdReader;
	private final MemberReader memberReader;

	@Override
	@Transactional
	public ReviewInfo.CreateReviewResponse createReview(final ReviewCommand.CreateReviewRequest command) {
		final Member findMember = memberReader.findById(command.getMemberId());
		final WantedJd findWantedJd = jdReader.findWantedJd(command.getJdId());
		final Review savedReview = reviewStore.save(command.toEntity(findMember, findWantedJd));

		return new ReviewInfo.CreateReviewResponse(savedReview.getId());
	}

	@Override
	public ReviewInfo.FindReviewListResponse getReviewList(final Long jdId, final PageInfoRequest pageInfoRequest) {
		return reviewReader.findReviewList(jdId, pageInfoRequest);
	}

	@Override
	@Transactional
	public void removeReview(final ReviewCommand.DeleteReviewRequest command) {
		final Review findReview = reviewReader.findById(command.getReviewId());
		validateDeleteReview(command, findReview);
		reviewStore.delete(findReview);
	}

	private void validateDeleteReview(final ReviewCommand.DeleteReviewRequest command, final Review findReview) {
		checkIfCreateMember(command, findReview);
	}

	private void checkIfCreateMember(final ReviewCommand.DeleteReviewRequest command, final Review findReview) {
		final Long loginMemberId = command.getMemberId();
		final Long createdMemberId = findReview.getMember().getId();
		if (!createdMemberId.equals(loginMemberId)) {
			throw new ApiException(ReviewErrorCode.FORBIDDEN_DELETE_REVIEW);
		}
	}
}
