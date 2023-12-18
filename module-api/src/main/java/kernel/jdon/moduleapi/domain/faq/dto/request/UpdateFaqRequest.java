package kernel.jdon.moduleapi.domain.faq.dto.request;

import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateFaqRequest {
	private Long faqId;
	private String title;
	private String content;
}
