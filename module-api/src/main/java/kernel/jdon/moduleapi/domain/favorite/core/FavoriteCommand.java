package kernel.jdon.moduleapi.domain.favorite.core;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteCommand {
	@Getter
	@Builder
	@AllArgsConstructor
	public static class UpdateRequest {
		private Long lectureId;
		@NotNull(message = "isFavorite은 null이 될 수 없습니다.")
		private Boolean isFavorite;
	}
}
