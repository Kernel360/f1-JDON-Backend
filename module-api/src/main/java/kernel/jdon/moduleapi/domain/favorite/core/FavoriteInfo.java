package kernel.jdon.moduleapi.domain.favorite.core;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import kernel.jdon.moduleapi.domain.favorite.infrastructure.FavoriteReaderInfo;
import kernel.jdon.moduleapi.global.page.CustomPageInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteInfo {

	@Getter
	@AllArgsConstructor
	public static class FindFavoriteListResponse {
		private List<FindFavorite> content;
		private CustomPageInfo pageInfo;
	}

	@Getter
	@AllArgsConstructor
	public static class FindFavorite {
		private Long lectureId;
		private String title;
		private String lectureUrl;
		private String imageUrl;
		private String instructor;
		private Long studentCount;
		private Integer price;

		public static FindFavorite of(FavoriteReaderInfo.FindFavoriteListResponse favorite) {
			return new FindFavorite(
				favorite.getLectureId(),
				favorite.getTitle(),
				favorite.getLectureUrl(),
				favorite.getImageUrl(),
				favorite.getInstructor(),
				favorite.getStudentCount(),
				favorite.getPrice()
			);
		}
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
