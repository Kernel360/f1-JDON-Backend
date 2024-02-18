package kernel.jdon.inflearncourse.repository;

import java.util.Optional;

import kernel.jdon.inflearncourse.domain.InflearnCourse;

public interface InflearnCourseRepository extends InflearnCourseDomainRepository {


	// 미사용
	Optional<InflearnCourse> findByTitle(String title);

	Optional<InflearnCourse> findById(Long id);

}
