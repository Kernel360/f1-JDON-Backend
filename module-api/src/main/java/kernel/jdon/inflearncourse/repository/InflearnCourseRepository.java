package kernel.jdon.inflearncourse.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import kernel.jdon.inflearncourse.domain.InflearnCourse;

@Repository("legacyInflearnCourseRepository")
public interface InflearnCourseRepository extends InflearnCourseDomainRepository {

	Optional<InflearnCourse> findById(Long id);

}
