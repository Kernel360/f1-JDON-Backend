package kernel.jdon.favorite.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FindFavoriteResponse {
	private Long lectureId;
	private String title;
	private String lectureUrl;
	private String imageUrl;
	private String instructor;
	private Integer studentCount;
	private Integer price;
	private Boolean isFavorite;
}
