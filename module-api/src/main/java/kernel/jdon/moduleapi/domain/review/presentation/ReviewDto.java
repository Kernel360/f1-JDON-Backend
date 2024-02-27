package kernel.jdon.moduleapi.domain.review.presentation;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDto {

	@Getter
	@Builder
	public static class CreateReviewRequest {
		@NotBlank(message = "jd 식별자가 없습니다.")
		private final Long jdId;
		@NotBlank(message = "내용이 없습니다.")
		private final String content;
	}

	@Getter
	@Builder
	public static class CreateReviewResponse {
		private final Long commandId;
	}
}
