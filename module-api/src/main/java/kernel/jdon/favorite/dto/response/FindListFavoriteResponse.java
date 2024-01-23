package kernel.jdon.favorite.dto.response;

import java.util.List;

import org.springframework.data.domain.Pageable;

import kernel.jdon.global.page.CustomPageInfo;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FindListFavoriteResponse {
	private CustomPageInfo<FindFavoriteResponse> customPageInfo;

	public static FindListFavoriteResponse of(List<InflearnCourse> inflearnCourseList, Pageable pageable,
		long totalCount) {
		List<FindFavoriteResponse> findFavoriteResponseList = inflearnCourseList.stream()
			.map(FindFavoriteResponse::of)
			.toList();

		CustomPageInfo<FindFavoriteResponse> customPageInfo = new CustomPageInfo<>(findFavoriteResponseList, pageable,
			totalCount);
		
		return new FindListFavoriteResponse(customPageInfo);
	}
}
