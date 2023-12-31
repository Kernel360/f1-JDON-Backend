package kernel.jdon.favorite.domain.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateFavoriteRequest {
	private Long lectureId;
	private Boolean isFavorite;
}
