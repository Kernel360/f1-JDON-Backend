package kernel.jdon.moduleapi.domain.inflearncourse.core;

import kernel.jdon.inflearncourse.domain.InflearnCourse;

public interface InflearnReader {
	InflearnCourse findById(Long lectureId);

}
