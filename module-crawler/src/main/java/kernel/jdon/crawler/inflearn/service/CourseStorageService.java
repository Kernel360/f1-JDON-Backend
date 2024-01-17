package kernel.jdon.crawler.inflearn.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.crawler.global.error.code.SkillErrorCode;
import kernel.jdon.crawler.global.error.exception.CrawlerException;
import kernel.jdon.crawler.inflearn.converter.EntityConverter;
import kernel.jdon.crawler.wanted.repository.InflearnCourseRepository;
import kernel.jdon.crawler.wanted.repository.InflearnJdSkillRepository;
import kernel.jdon.crawler.wanted.repository.SkillRepository;
import kernel.jdon.inflearncourse.domain.InflearnCourse;
import kernel.jdon.inflearnjdskill.domain.InflearnJdSkill;
import kernel.jdon.skill.domain.Skill;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseStorageService {

	private final InflearnCourseRepository inflearnCourseRepository;
	private final InflearnJdSkillRepository inflearnJdSkillRepository;
	private final SkillRepository skillRepository;

	@Transactional
	public void createInflearnCourseAndInflearnJdSkill(String skillKeyword, List<InflearnCourse> newCourseList) {
		Skill findSkill = skillRepository.findByKeyword(skillKeyword)
			.orElseThrow(() -> new CrawlerException(SkillErrorCode.NOT_FOUND_SKILL));
		deleteExistingInflearnJdSkills(findSkill);
		createOrUpdateCourseList(newCourseList, findSkill);
	}

	private void deleteExistingInflearnJdSkills(Skill skill) {
		List<InflearnJdSkill> findJdSkills = inflearnJdSkillRepository.findBySkill(skill);
		inflearnJdSkillRepository.deleteAll(findJdSkills);
	}

	private void createOrUpdateCourseList(List<InflearnCourse> inflearnCourseList, Skill skill) {
		for (InflearnCourse inflearnCourse : inflearnCourseList) {
			InflearnCourse createCourse = createOrUpdateCourse(inflearnCourse);
			createInflearnJdSkill(createCourse, skill);
		}
	}

	private InflearnCourse createOrUpdateCourse(InflearnCourse course) {
		return inflearnCourseRepository.findByTitle(course.getTitle())
			.orElseGet(() -> inflearnCourseRepository.save(course));
	}

	private void createInflearnJdSkill(InflearnCourse course, Skill skill) {
		InflearnJdSkill createJdSkill = EntityConverter.createInflearnJdSkill(course, skill);
		inflearnJdSkillRepository.save(createJdSkill);
	}
}
