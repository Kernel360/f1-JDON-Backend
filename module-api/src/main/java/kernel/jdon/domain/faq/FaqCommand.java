package kernel.jdon.domain.faq;

import kernel.jdon.faq.domain.Faq;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FaqCommand {
	@Getter
	@Builder
	public static class CreateRequest {
		private String title;
		private String content;

		public Faq toEntity() {
			return Faq.builder()
				.title(title)
				.content(content)
				.build();
		}
	}

	@Getter
	@Builder
	public static class UpdateRequest {
		private Long faqId;
		private String title;
		private String content;
	}
}
