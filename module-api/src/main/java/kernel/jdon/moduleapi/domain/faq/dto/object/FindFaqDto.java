package kernel.jdon.moduleapi.domain.faq.dto.object;

import kernel.jdon.moduleapi.domain.faq.entity.Faq;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindFaqDto {
	private Long id;
	private String title;
	private String content;

	public static FindFaqDto of(Faq faq) {
		return FindFaqDto.builder()
			.id(faq.getId())
			.title(faq.getTitle())
			.content(faq.getContent())
			.build();
	}
}
