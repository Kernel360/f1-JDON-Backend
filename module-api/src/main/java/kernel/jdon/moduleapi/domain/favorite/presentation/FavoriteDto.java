package kernel.jdon.moduleapi.domain.favorite.presentation;

import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfo;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteDto {

	@Getter
	@AllArgsConstructor
	public static class FindPageResponse {
		private CustomPageResponse<FavoriteInfo.FindResponse> findPage;
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
	@AllArgsConstructor
	public static class UpdateResponse {
		private Long lectureId;
	}
}
