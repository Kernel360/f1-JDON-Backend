package kernel.jdon.crawler.inflearn.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kernel.jdon.inflearn.domain.InflearnCourse;

@Repository
public interface InflearnCourseRepository extends JpaRepository<InflearnCourse, Long> {

	Optional<InflearnCourse> findByCourseId(Long courseId);
}
