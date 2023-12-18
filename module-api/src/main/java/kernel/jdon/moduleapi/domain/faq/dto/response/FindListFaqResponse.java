package kernel.jdon.moduleapi.domain.faq.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import kernel.jdon.moduleapi.domain.faq.dto.object.FindFaqDto;
import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindListFaqResponse {
	private List<FindFaqDto> faqList;

	public static FindListFaqResponse of(List<Faq> faqList) {
		List<FindFaqDto> faqDtoList = faqList.stream()
			.map(FindFaqDto::of)
			.collect(Collectors.toList());

		return FindListFaqResponse.builder()
			.faqList(faqDtoList)
			.build();
	}
}
