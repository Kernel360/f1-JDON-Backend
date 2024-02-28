package kernel.jdon.moduledomain.inflearncourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;

public interface InflearnCourseDomainRepository extends JpaRepository<InflearnCourse, Long> {
}
