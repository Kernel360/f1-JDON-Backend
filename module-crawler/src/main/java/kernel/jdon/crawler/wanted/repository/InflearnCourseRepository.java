package kernel.jdon.crawler.wanted.repository;

import java.util.Optional;

import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.inflearncourse.repository.InflearnCourseDomainRepository;

public interface InflearnCourseRepository extends InflearnCourseDomainRepository {
	Optional<InflearnCourse> findByTitle(String title);
}
