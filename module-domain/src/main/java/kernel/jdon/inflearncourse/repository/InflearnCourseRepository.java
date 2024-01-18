package kernel.jdon.inflearncourse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.inflearncourse.domain.InflearnCourse;

public interface InflearnCourseRepository extends JpaRepository<InflearnCourse, Long> {

	Optional<InflearnCourse> findByTitle(String title);

	Optional<InflearnCourse> findById(Long id);

}
