package kernel.jdon.moduleapi.domain.faq.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindAllFaqResponse {
	private List<FindFaqDto> faqList;

	public static FindAllFaqResponse of(List<Faq> faqList) {
		List<FindFaqDto> faqDtoList = faqList.stream()
			.map(FindFaqDto::of)
			.collect(Collectors.toList());

		return FindAllFaqResponse.builder()
			.faqList(faqDtoList)
			.build();
	}
}
