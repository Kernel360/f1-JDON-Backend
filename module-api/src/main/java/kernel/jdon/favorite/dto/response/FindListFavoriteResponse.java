package kernel.jdon.favorite.dto.response;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.global.page.CustomPageResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindListFavoriteResponse {
	private CustomPageResponse<FindFavoriteResponse> customPageInfo;

	public static FindListFavoriteResponse of(Page<Favorite> findFavoritePage, Pageable pageable) {
		Page<FindFavoriteResponse> responsePage = findFavoritePage.map(favorite ->
			FindFavoriteResponse.of(favorite.getInflearnCourse())
		);

		CustomPageResponse<FindFavoriteResponse> customPageInfo = CustomPageResponse.of(responsePage);

		return new FindListFavoriteResponse(customPageInfo);
	}
}
