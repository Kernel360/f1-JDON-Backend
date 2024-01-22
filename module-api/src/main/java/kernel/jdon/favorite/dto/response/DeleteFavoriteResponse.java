package kernel.jdon.favorite.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteFavoriteResponse {
	private Long lectureId;

	public static DeleteFavoriteResponse of(Long lectureId) {
		return new DeleteFavoriteResponse(lectureId);
	}
}
