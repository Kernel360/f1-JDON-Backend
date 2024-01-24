package kernel.jdon.skill.dto.object;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FindWantedJdDto {
	private String company;
	private String title;
	private String imageUrl;
	private String jdUrl;

	@QueryProjection
	public FindWantedJdDto(String company, String title, String imageUrl, String jdUrl) {
		this.company = company;
		this.title = title;
		this.imageUrl = imageUrl;
		this.jdUrl = jdUrl;
	}
}
