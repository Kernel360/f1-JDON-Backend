package kernel.jdon.interfaces.faq;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import kernel.jdon.domain.faq.FaqCommand;
import kernel.jdon.domain.faq.FaqInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FaqDto {
	@Getter
	public static class CreateRequest {
		@NotEmpty(message = "제목은 필수 입력 항목 입니다.")
		private String title;
		@NotEmpty(message = "내용은 필수 입력 항목 입니다.")
		private String content;
	}

	@Getter
	@Builder
	public static class CreateResponse {
		private Long faqId;
	}

	@Getter
	public static class UpdateRequest {
		@NotNull(message = "식별자는 필수 입력 항목 입니다.")
		private Long faqId;
		private String title;
		private String content;

		public FaqCommand.CreateRequest toCommand() {
			return FaqCommand.CreateRequest.builder()
				.title(title)
				.content(content)
				.build();
		}
	}

	@Getter
	@Builder
	public static class UpdateResponse {
		private Long faqId;
	}

	@Getter
	@Builder
	public static class DeleteResponse {
		private Long faqId;
	}

	@Getter
	@Builder
	public static class FindListResponse {
		private List<FaqInfo.FindResponse> faqList;
	}
}
