package kernel.jdon.moduleapi.domain.faq.core;

import kernel.jdon.moduledomain.faq.domain.Faq;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FaqCommand {

	@Getter
	@Builder
	public static class CreateFaqRequest {
		private final String title;
		private final String content;

		public Faq toEntity() {
			return Faq.builder()
				.title(title)
				.content(content)
				.build();
		}
	}

	@Getter
	@Builder
	public static class UpdateFaqRequest {
		private final Long faqId;
		private final String title;
		private final String content;
	}
}
