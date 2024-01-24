package kernel.jdon.favorite.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.global.page.CustomPageInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindListFavoriteResponse {
	private CustomPageInfo<FindFavoriteResponse> customPageInfo;

	public static FindListFavoriteResponse of(Page<Favorite> findFavoritePage, Pageable pageable) {
		List<FindFavoriteResponse> findFavoriteResponseList = findFavoritePage.getContent().stream()
			.map(Favorite::getInflearnCourse)
			.map(FindFavoriteResponse::of)
			.toList();

		CustomPageInfo<FindFavoriteResponse> customPageInfo = new CustomPageInfo<>(findFavoriteResponseList, pageable,
			findFavoritePage.getTotalElements());

		return new FindListFavoriteResponse(customPageInfo);
	}
}
