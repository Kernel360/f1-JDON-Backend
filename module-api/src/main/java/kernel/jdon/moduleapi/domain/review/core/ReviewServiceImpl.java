package kernel.jdon.moduleapi.domain.review.core;

import org.springframework.stereotype.Service;

import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.jd.core.JdReader;
import kernel.jdon.moduleapi.domain.member.core.MemberReader;
import kernel.jdon.moduledomain.review.domain.Review;
import kernel.jdon.wantedjd.domain.WantedJd;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
	private final ReviewStore reviewStore;
	private final JdReader jdReader;
	private final MemberReader memberReader;

	@Override
	public ReviewInfo.CreateReviewResponse createReview(ReviewCommand.CreateReviewRequest command) {
		final Member findMember = memberReader.findById(command.getMemberId());
		final WantedJd findWantedJd = jdReader.findWantedJd(command.getJdId());
		final Review savedReview = reviewStore.save(command.toEntity(findMember, findWantedJd));

		return new ReviewInfo.CreateReviewResponse(savedReview.getId());
	}
}
