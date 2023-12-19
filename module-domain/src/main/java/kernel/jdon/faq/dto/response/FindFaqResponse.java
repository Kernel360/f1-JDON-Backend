package kernel.jdon.faq.dto.response;

import kernel.jdon.faq.domain.Faq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FindFaqResponse {
	private String title;
	private String content;

	public static FindFaqResponse of(Faq faq) {
		return FindFaqResponse.builder()
			.title(faq.getTitle())
			.content(faq.getContent())
			.build();
	}
}
