package kernel.jdon.moduleapi.domain.faq.dto;

import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateFaqResponse {
	private Long id;
	private String title;
	private String content;

	public static CreateFaqResponse of(Faq faq) {
		return CreateFaqResponse.builder()
			.id(faq.getId())
			.title(faq.getTitle())
			.content(faq.getContent())
			.build();
	}
}
