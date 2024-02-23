package kernel.jdon.moduleapi.domain.favorite.core;

import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.member.domain.Member;

public interface FavoriteStore {
	FavoriteInfo.UpdateResponse save(Member member, InflearnCourse inflearnCourse);

	FavoriteInfo.UpdateResponse delete(Long memberId, Long lectureId);
}
