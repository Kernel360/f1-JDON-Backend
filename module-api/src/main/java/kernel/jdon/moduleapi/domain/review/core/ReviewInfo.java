package kernel.jdon.moduleapi.domain.review.core;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewInfo {

	@Getter
	@AllArgsConstructor
	public static class CreateReviewResponse {
		private final Long commandId;
	}
}
