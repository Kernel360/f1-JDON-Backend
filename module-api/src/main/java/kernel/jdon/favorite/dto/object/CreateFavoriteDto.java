package kernel.jdon.favorite.dto.object;

import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateFavoriteDto {
	private Member member;
	private InflearnCourse inflearnCourse;
}
