package kernel.jdon.favorite.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateFavoriteResponse {
	private Long lectureId;

	public static CreateFavoriteResponse of(Long lectureId) {
		return new CreateFavoriteResponse(lectureId);
	}
}
