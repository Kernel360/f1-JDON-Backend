package kernel.jdon.moduleapi.domain.favorite.presentation;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import kernel.jdon.moduleapi.global.page.CustomPageInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteDto {

	// @Getter
	// @AllArgsConstructor
	// public static class FindPageResponse {
	// 	private CustomPageResponse<FavoriteInfo.FindResponse> findPage;
	// }

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
	@AllArgsConstructor
	public static class FindFavoriteListResponse {
		private List<FavoriteDto.FindFavoriteDto> content;
		private CustomPageInfo pageInfo;
	}

	@Getter
	@AllArgsConstructor
	public static class FindFavoriteDto {
		private Long lectureId;
		private String title;
		private String lectureUrl;
		private String imageUrl;
		private String instructor;
		private Long studentCount;
		private Integer price;
	}

	@Getter
	public static class UpdateRequest {
		private Long lectureId;
		@NotNull(message = "isFavorite은 null이 될 수 없습니다.")
		private Boolean isFavorite;
	}

	@Getter
	@AllArgsConstructor
	public static class UpdateResponse {
		private Long lectureId;
	}
}
