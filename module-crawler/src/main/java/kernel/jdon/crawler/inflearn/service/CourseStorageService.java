package kernel.jdon.crawler.inflearn.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import kernel.jdon.crawler.inflearn.converter.EntityConverter;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.inflearncourse.repository.InflearnCourseRepository;
import kernel.jdon.inflearnjdskill.domain.InflearnJdSkill;
import kernel.jdon.inflearnjdskill.repository.InflearnJdSkillRepository;
import kernel.jdon.skill.domain.Skill;
import kernel.jdon.skill.repository.SkillRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseStorageService {

	private final InflearnCourseRepository inflearnCourseRepository;
	private final InflearnJdSkillRepository inflearnJdSkillRepository;
	private final SkillRepository skillRepository;

	protected void createInflearnCourseAndInflearnJdSkill(String skillKeyword, List<InflearnCourse> newCourseList) {
		Skill findSkill = skillRepository.findByKeyword(skillKeyword)
			.orElseThrow(() -> new IllegalArgumentException("추가하기"));
		deleteExistingInflearnJdSkills(findSkill);
		createInflearnCourses(findSkill, newCourseList);
	}

	private void deleteExistingInflearnJdSkills(Skill skill) {
		List<InflearnJdSkill> findJdSkills = inflearnJdSkillRepository.findBySkill(skill);
		inflearnJdSkillRepository.deleteAll(findJdSkills);
	}

	private void createInflearnCourses(Skill skill, List<InflearnCourse> inflearnCourseList
	) {
		for (InflearnCourse inflearnCourse : inflearnCourseList) {
			Optional<InflearnCourse> findCourseOptional = inflearnCourseRepository.findByTitle(
				inflearnCourse.getTitle());

			if (findCourseOptional.isPresent()) {
				InflearnCourse findCourse = findCourseOptional.get();
				inflearnCourseRepository.save(findCourse);
				createInflearnJdSkill(findCourse, skill);
			} else {
				InflearnCourse saveCourse = inflearnCourseRepository.save(inflearnCourse);
				createInflearnJdSkill(saveCourse, skill);
			}
		}
	}

	private void createInflearnJdSkill(InflearnCourse course, Skill skill) {
		InflearnJdSkill createJdSkill = EntityConverter.createInflearnJdSkill(course, skill);
		inflearnJdSkillRepository.save(createJdSkill);
	}
}
