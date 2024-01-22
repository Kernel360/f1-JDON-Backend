package kernel.jdon.favorite.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateFavoriteResponse {
	private Long lectureId;

	public static UpdateFavoriteResponse of(Long lectureId) {
		return new UpdateFavoriteResponse(lectureId);
	}
}
