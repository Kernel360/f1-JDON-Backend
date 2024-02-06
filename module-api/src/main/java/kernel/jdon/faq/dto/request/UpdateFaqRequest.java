package kernel.jdon.faq.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateFaqRequest {
	private Long faqId;
	private String title;
	private String content;
}
