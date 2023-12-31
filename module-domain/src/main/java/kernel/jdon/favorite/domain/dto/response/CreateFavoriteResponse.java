package kernel.jdon.favorite.domain.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateFavoriteResponse {
	private Long lectureId;

	public static CreateFavoriteResponse of(Long lectureId) {
		return new CreateFavoriteResponse(lectureId);
	}
}
