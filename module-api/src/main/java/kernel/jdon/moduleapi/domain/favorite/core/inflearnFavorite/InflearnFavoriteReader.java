package kernel.jdon.moduleapi.domain.favorite.core.inflearnFavorite;

import kernel.jdon.inflearncourse.domain.InflearnCourse;

public interface InflearnFavoriteReader {
	InflearnCourse findById(Long lectureId);

}
