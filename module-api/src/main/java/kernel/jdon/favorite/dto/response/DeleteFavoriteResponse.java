package kernel.jdon.favorite.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class DeleteFavoriteResponse {
	private Long lectureId;

	public static DeleteFavoriteResponse of(Long lectureId) {
		return new DeleteFavoriteResponse(lectureId);
	}
}
