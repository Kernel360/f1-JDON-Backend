package kernel.jdon.moduleapi.domain.faq.dto.response;

import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateFaqResponse {

	private Long faqId;

	public static CreateFaqResponse of(Faq faq) {
		return new CreateFaqResponse(faq.getId());
	}
}
