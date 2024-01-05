package kernel.jdon.crawler.inflearn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.inflearn.domain.InflearnCourse;

public interface InflearnCourseRepository extends JpaRepository<InflearnCourse, Long> {

	Optional<InflearnCourse> findByCourseId(Long courseId);
}
