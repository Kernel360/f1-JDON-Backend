package kernel.jdon.moduleapi.domain.review.presentation;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kernel.jdon.moduleapi.global.page.CustomSliceInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDto {

	@Getter
	@Builder
	public static class CreateReviewRequest {
		@NotNull(message = "jd 식별자가 없습니다.")
		private final Long jdId;
		@NotBlank(message = "내용은 필수 입력 항목 입니다.")
		@Size(min = 10, max = 500, message = "내용은 10자 이상 500자 이하로 작성해주세요.")
		private final String content;
	}

	@Getter
	@Builder
	public static class CreateReviewResponse {
		private final Long reviewId;
	}

	@Getter
	@Builder
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
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private final LocalDateTime createdDate;
		private final Long memberId;
	}

}
