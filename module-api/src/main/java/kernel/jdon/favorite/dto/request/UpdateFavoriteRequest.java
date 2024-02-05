package kernel.jdon.favorite.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateFavoriteRequest {
	private Long lectureId;
	@NotNull(message = "isFavorite은 null이 될 수 없습니다.")
	private Boolean isFavorite;

	@Builder
	public UpdateFavoriteRequest(Long lectureId, Boolean isFavorite) {
		this.lectureId = lectureId;
		this.isFavorite = isFavorite;
	}
}
