package kernel.jdon.faq.dto.request;

import kernel.jdon.faq.domain.Faq;
import lombok.Getter;

@Getter
public class CreateFaqRequest {
	private String title;
	private String content;

	public static Faq toEntity(CreateFaqRequest createFaqRequest) {
		return Faq.builder()
			.title(createFaqRequest.title)
			.content(createFaqRequest.content)
			.build();
	}
}
