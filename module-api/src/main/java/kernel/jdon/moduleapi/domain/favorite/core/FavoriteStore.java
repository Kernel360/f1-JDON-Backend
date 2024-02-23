package kernel.jdon.moduleapi.domain.favorite.core;

import kernel.jdon.favorite.domain.Favorite;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.member.domain.Member;

public interface FavoriteStore {
	Favorite save(Member member, InflearnCourse inflearnCourse);

	Favorite delete(Long memberId, Long lectureId);
}
