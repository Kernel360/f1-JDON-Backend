package kernel.jdon.faq.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteFaqResponse {

	private Long faqId;

	public static DeleteFaqResponse of(Long faqId) {
		return new DeleteFaqResponse(faqId);
	}
}
