package kernel.jdon.moduleapi.domain.favorite.core;

import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.member.domain.Member;

public interface FavoriteStore {
	FavoriteInfo.UpdateResponse create(Member member, InflearnCourse inflearnCourse);

	void delete(Long memberId, Long lectureId);
}
