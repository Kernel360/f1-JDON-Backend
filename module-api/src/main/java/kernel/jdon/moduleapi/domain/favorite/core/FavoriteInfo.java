package kernel.jdon.moduleapi.domain.favorite.core;

import jakarta.validation.constraints.NotNull;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteInfo {

	@Getter
	@Builder
	@AllArgsConstructor
	public static class FindPageResponse {
		private CustomPageResponse<FindResponse> findPage;
	}

	@Getter
	@AllArgsConstructor
	public static class FindResponse {
		private Long lectureId;
		private String title;
		private String lectureUrl;
		private String imageUrl;
		private String instructor;
		private Long studentCount;
		private Integer price;
	}

	@Getter
	public class UpdateRequest {
		private Long lectureId;
		@NotNull(message = "isFavorite은 null이 될 수 없습니다.")
		private Boolean isFavorite;
	}

	@Getter
	@AllArgsConstructor
	@Builder
	public static class UpdateResponse {
		private Long lectureId;
	}
}
