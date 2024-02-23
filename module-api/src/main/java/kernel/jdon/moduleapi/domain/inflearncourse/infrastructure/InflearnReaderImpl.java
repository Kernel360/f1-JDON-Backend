package kernel.jdon.moduleapi.domain.inflearncourse.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.moduleapi.domain.inflearncourse.core.InflearnReader;
import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InflearnReaderImpl implements InflearnReader {

	private final InflearnCourseRepository inflearnCourseRepository;

	@Override
	public InflearnCourse findById(Long lectureId) {
		return inflearnCourseRepository.findById(lectureId)
			.orElseThrow(MemberErrorCode.NOT_FOUND_MEMBER::throwException);
	}
}