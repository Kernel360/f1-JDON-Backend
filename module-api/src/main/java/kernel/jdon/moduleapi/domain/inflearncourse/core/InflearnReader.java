package kernel.jdon.moduleapi.domain.inflearncourse.core;

import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;

public interface InflearnReader {
	InflearnCourse findById(Long lectureId);

}
