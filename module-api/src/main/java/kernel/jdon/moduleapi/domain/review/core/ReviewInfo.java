package kernel.jdon.moduleapi.domain.review.core;

import java.time.LocalDateTime;
import java.util.List;

import kernel.jdon.moduleapi.global.page.CustomSliceInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewInfo {

	@Getter
	@AllArgsConstructor
	public static class CreateReviewResponse {
		private final Long reviewId;
	}

	@Getter
	@AllArgsConstructor
	public static class FindReviewListResponse {
		private final List<FindReview> content;
		private final CustomSliceInfo pageInfo;
	}

	@Getter
	@Builder
	public static class FindReview {
		private final Long id;
		private final String content;
		private final String nickname;
		private final LocalDateTime createdDate;
		private final Long memberId;
	}
}
