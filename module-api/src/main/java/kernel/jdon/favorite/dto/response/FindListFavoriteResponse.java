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
	// TODO: customPageInfo로 만들지 favoriteList와 pageInfo로 만들어서 Controller에서 한번에 반환할지 정해야합니다.
	// private CustomPageInfo<FindFavoriteResponse> customPageInfo;
	private List<FindFavoriteResponse> content;
	private CustomPageInfo.PageInfo pageInfo;

	public static FindListFavoriteResponse of(Page<Favorite> findFavoritePage, Pageable pageable) {
		List<FindFavoriteResponse> findFavoriteResponseList = findFavoritePage.getContent().stream()
			.map(Favorite::getInflearnCourse)
			.map(FindFavoriteResponse::of)
			.toList();

		// CustomPageInfo<FindFavoriteResponse> customPageInfo = new CustomPageInfo<>(findFavoriteResponseList, pageable,
		// 	findFavoritePage.getTotalElements());
		//
		// return new FindListFavoriteResponse(customPageInfo);
		CustomPageInfo<FindFavoriteResponse> customPageInfo = new CustomPageInfo<>(findFavoriteResponseList, pageable,
			findFavoritePage.getTotalElements());
		CustomPageInfo.PageInfo pageInfo = customPageInfo.getPageInfo();

		return new FindListFavoriteResponse(findFavoriteResponseList, pageInfo);
	}
}
