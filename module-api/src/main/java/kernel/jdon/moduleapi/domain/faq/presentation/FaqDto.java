package kernel.jdon.moduleapi.domain.faq.presentation;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FaqDto {
	@Getter
	public static class CreateFaqRequest {
		@NotEmpty(message = "제목은 필수 입력 항목 입니다.")
		private String title;
		@NotEmpty(message = "내용은 필수 입력 항목 입니다.")
		private String content;
	}

	@Getter
	@Builder
	public static class CreateFaqResponse {
		private final Long faqId;
	}

	@Getter
	public static class UpdateFaqRequest {
		@NotNull(message = "식별자는 필수 입력 항목 입니다.")
		private Long faqId;
		private String title;
		private String content;
	}

	@Getter
	@Builder
	public static class UpdateFaqResponse {
		private final Long faqId;
	}

	@Getter
	@Builder
	public static class DeleteFaqResponse {
		private final Long faqId;
	}

	@Getter
	@Builder
	public static class FindFaqListResponse {
		private final List<FindFaq> faqList;
	}

	@Getter
	@Builder
	public static class FindFaq {
		private final Long faqId;
		private final String title;
		private final String content;
	}
}
