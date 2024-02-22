package kernel.jdon.moduleapi.domain.favorite.infrastructure.inflearnFavorite;

import org.springframework.stereotype.Component;

import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.moduleapi.domain.favorite.core.inflearnFavorite.InflearnFavoriteReader;
import kernel.jdon.moduleapi.domain.inflearncourse.infrastructure.InflearnCourseRepository;
import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InflearnFavoriteReaderImpl implements InflearnFavoriteReader {

	private final InflearnCourseRepository inflearnCourseRepository;

	@Override
	public InflearnCourse findById(Long lectureId) {
		return inflearnCourseRepository.findById(lectureId)
			.orElseThrow(MemberErrorCode.NOT_FOUND_MEMBER::throwException);
	}
}