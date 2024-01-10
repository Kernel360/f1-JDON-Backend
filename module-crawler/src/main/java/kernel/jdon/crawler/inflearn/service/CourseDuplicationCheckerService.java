package kernel.jdon.crawler.inflearn.service;

import org.springframework.stereotype.Service;

import kernel.jdon.inflearncourse.repository.InflearnCourseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseDuplicationCheckerService {

	private final InflearnCourseRepository inflearnCourseRepository;

	protected boolean isDuplicate(Long courseId) {
		if (courseId == null) {
			return false;
		}

		return inflearnCourseRepository.findByCourseId(courseId).isPresent();
	}
}
