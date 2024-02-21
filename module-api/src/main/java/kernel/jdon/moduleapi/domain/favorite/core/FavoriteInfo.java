package kernel.jdon.moduleapi.domain.favorite.core;

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
	@AllArgsConstructor
	@Builder
	public static class UpdateResponse {
		private Long lectureId;
	}
}
