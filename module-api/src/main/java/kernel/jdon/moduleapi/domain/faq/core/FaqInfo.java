package kernel.jdon.moduleapi.domain.faq.core;

import java.util.List;

import kernel.jdon.moduledomain.faq.domain.Faq;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FaqInfo {

	@Getter
	@Builder
	public static class CreateFaqResponse {
		private final Long faqId;
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
	public static class FindFaqListResponse {
		private final List<FindFaq> faqList;

		public FindFaqListResponse(List<Faq> faqList) {
			this.faqList = faqList.stream()
				.map(FindFaq::new)
				.toList();
		}
	}

	@Getter
	public static class FindFaq {
		private final Long faqId;
		private final String title;
		private final String content;

		public FindFaq(Faq faq) {
			this.faqId = faq.getId();
			this.title = faq.getTitle();
			this.content = faq.getContent();
		}
	}
}
