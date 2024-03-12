package kernel.jdon.moduleapi.domain.inflearncourse.infrastructure;

import org.springframework.stereotype.Component;

import kernel.jdon.moduleapi.domain.inflearncourse.core.InflearnReader;
import kernel.jdon.moduleapi.domain.inflearncourse.error.InflearncourseErrorCode;
import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InflearnReaderImpl implements InflearnReader {

    private final InflearnCourseRepository inflearnCourseRepository;

    @Override
    public InflearnCourse findById(Long lectureId) {
        return inflearnCourseRepository.findById(lectureId)
            .orElseThrow(InflearncourseErrorCode.NOT_FOUND_INFLEARN_COURSE::throwException);
    }
}