package kernel.jdon.moduleapi.domain.faq.dto.response;

import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteFaqResponse {

	private Long faqId;

	public static DeleteFaqResponse of(Faq faq) {
		return new DeleteFaqResponse(faq.getId());
	}
}
