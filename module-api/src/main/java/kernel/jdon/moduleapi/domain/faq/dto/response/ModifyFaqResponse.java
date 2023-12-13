package kernel.jdon.moduleapi.domain.faq.dto.response;

import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ModifyFaqResponse {

	private Long faqId;

	public static ModifyFaqResponse of(Faq faq) {
		return new ModifyFaqResponse(faq.getId());
	}
}
