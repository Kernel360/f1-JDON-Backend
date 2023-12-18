package kernel.jdon.moduleapi.domain.faq.dto.response;

import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateFaqResponse {

	private Long faqId;

	public static UpdateFaqResponse of(Faq faq) {
		return new UpdateFaqResponse(faq.getId());
	}
}
