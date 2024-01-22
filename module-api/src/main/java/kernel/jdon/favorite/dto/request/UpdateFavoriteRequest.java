package kernel.jdon.favorite.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateFavoriteRequest {
	private Long lectureId;
	private Boolean isFavorite;
}
