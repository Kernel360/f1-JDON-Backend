package kernel.jdon.faq.dto.request;

import kernel.jdon.faq.domain.Faq;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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
