package kernel.jdon.moduleapi.domain.faq.dto.request;

import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ModifyFaqRequest {
	private Long faqId;
	private String title;
	private String content;

	public Faq toEntity() {
		return Faq.builder()
			.title(title)
			.content(content)
			.build();
	}
}
