package kernel.jdon.inflearncourse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.inflearncourse.domain.InflearnCourse;

public interface InflearnCourseDomainRepository extends JpaRepository<InflearnCourse, Long> {
}
