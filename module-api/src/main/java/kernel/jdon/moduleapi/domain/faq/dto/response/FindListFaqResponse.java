package kernel.jdon.moduleapi.domain.faq.dto.response;

import java.util.List;

import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindListFaqResponse {
	private List<FindFaqResponse> faqList;

	public static FindListFaqResponse of(List<Faq> faqList) {
		List<FindFaqResponse> findFaqResponseList = faqList.stream()
			.map(FindFaqResponse::of)
			.toList();

		return FindListFaqResponse.builder()
			.faqList(findFaqResponseList)
			.build();
	}
}
