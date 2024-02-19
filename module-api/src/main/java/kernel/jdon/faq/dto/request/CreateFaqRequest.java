package kernel.jdon.faq.dto.request;

import kernel.jdon.moduledomain.faq.domain.Faq;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateFaqRequest {
	private String title;
	private String content;

	public Faq toEntity() {
		return Faq.builder()
			.title(this.title)
			.content(this.content)
			.build();
	}
}
