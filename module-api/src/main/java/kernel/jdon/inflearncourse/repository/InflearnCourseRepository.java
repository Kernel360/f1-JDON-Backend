package kernel.jdon.inflearncourse.repository;

import java.util.Optional;

import kernel.jdon.inflearncourse.domain.InflearnCourse;

public interface InflearnCourseRepository extends InflearnCourseDomainRepository {

	Optional<InflearnCourse> findByTitle(String title);

	Optional<InflearnCourse> findById(Long id);

}
