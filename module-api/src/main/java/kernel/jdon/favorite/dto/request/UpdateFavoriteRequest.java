package kernel.jdon.favorite.dto.request;

import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.favorite.dto.object.CreateFavoriteDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateFavoriteRequest {
	private Long lectureId;
	private Boolean isFavorite;

	public static Favorite toEntity(CreateFavoriteDto createFavoriteDto) {
		return Favorite.builder()
			.member(createFavoriteDto.getMember())
			.inflearnCourse(createFavoriteDto.getInflearnCourse())
			.build();
	}
}
