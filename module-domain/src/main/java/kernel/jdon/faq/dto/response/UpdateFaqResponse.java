package kernel.jdon.faq.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateFaqResponse {

	private Long faqId;

	public static UpdateFaqResponse of(Long faqId) {
		return new UpdateFaqResponse(faqId);
	}
}
