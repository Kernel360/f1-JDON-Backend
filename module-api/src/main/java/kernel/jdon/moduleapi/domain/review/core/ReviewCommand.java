package kernel.jdon.moduleapi.domain.review.core;

import kernel.jdon.member.domain.Member;
import kernel.jdon.moduledomain.review.domain.Review;
import kernel.jdon.wantedjd.domain.WantedJd;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewCommand {

	@Getter
	@Builder
	public static class CreateReviewRequest {
		private final Long jdId;
		private final String content;
		private final Long memberId;

		public Review toEntity(Member member, WantedJd wantedJd) {
			return Review.builder()
				.content(this.content)
				.member(member)
				.wantedJd(wantedJd)
				.build();
		}
	}

	@Getter
	@Builder
	public static class DeleteReviewRequest {
		private final Long reviewId;
		private final Long memberId;
	}
}
