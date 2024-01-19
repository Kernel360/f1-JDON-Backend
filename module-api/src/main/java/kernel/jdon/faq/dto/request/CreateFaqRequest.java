package kernel.jdon.faq.dto.request;

import kernel.jdon.faq.domain.Faq;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
