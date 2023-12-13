package kernel.jdon.moduleapi.domain.faq.dto;

import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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
