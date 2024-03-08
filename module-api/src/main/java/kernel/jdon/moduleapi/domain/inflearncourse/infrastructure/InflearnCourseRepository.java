package kernel.jdon.moduleapi.domain.inflearncourse.infrastructure;

import java.util.Optional;

import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import kernel.jdon.moduledomain.inflearncourse.repository.InflearnCourseDomainRepository;

public interface InflearnCourseRepository extends InflearnCourseDomainRepository {
	
	Optional<InflearnCourse> findById(Long id);

}
