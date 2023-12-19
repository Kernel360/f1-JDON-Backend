package kernel.jdon.domain.faq.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateFaqResponse {

	private Long faqId;

	public static CreateFaqResponse of(Long faqId) {
		return new CreateFaqResponse(faqId);
	}
}
