package kernel.jdon.moduleapi.domain.faq.core;

import java.util.List;

import kernel.jdon.moduledomain.faq.domain.Faq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FaqInfo {

	@Getter
	public static class CreateResponse {
		private Long faqId;

		public CreateResponse(Faq faq) {
			this.faqId = faq.getId();
		}
	}

	@Getter
	public static class UpdateResponse {
		private Long faqId;

		public UpdateResponse(Faq faq) {
			this.faqId = faq.getId();
		}
	}
	@Getter
	public static class DeleteResponse {
		private Long faqId;

		public DeleteResponse(Faq faq) {
			this.faqId = faq.getId();
		}
	}

	@Getter
	public static class FindListResponse {
		private List<FindResponse> faqList;

		public FindListResponse(List<Faq> faqList) {
			this.faqList = faqList.stream()
				.map(FindResponse::new)
				.toList();

		}
	}

	@Getter
	public static class FindResponse {
		private Long faqId;
		private String title;
		private String content;

		public FindResponse(Faq faq) {
			this.faqId = getFaqId();
			this.title = faq.getTitle();
			this.content = faq.getContent();
		}
	}
}
