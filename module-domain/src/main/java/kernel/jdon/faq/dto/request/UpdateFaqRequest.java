package kernel.jdon.faq.dto.request;

import lombok.Getter;

@Getter
public class UpdateFaqRequest {
	private Long faqId;
	private String title;
	private String content;
}
