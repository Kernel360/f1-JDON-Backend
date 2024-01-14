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
import kernel.jdon.wantedjdskill.domain.WantedJdSkill;
import kernel.jdon.wantedjdskill.repository.WantedJdSkillRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseStorageService {

	private final InflearnCourseRepository inflearnCourseRepository;
	private final InflearnJdSkillRepository inflearnJdSkillRepository;
	private final WantedJdSkillRepository wantedJdSkillRepository;
	private final SkillRepository skillRepository;
	private final CourseDuplicationCheckerService courseDuplicationCheckerService;

	protected void createInflearnCourseAndInflearnJdSkill(String skillKeyword, List<InflearnCourse> newCourseList) {
		Optional<Skill> findSkillOptional = skillRepository.findByKeyword(skillKeyword);
		if (findSkillOptional.isPresent()) {
			Skill skill = findSkillOptional.get();
			List<WantedJdSkill> findWantedJdSkillList = wantedJdSkillRepository.findBySkill(skill);
			System.out.println("findWantedJdSkillList: " + findWantedJdSkillList.toString());
			// deleteExistingInflearnJdSkills(skill);
			createInflearnCourses(skill, newCourseList, findWantedJdSkillList);
		}
	}

	private void deleteExistingInflearnJdSkills(Skill skill) {
		List<InflearnJdSkill> existingJdSkills = inflearnJdSkillRepository.findBySkill(skill);
		System.out.println("existingJdSkills: " + existingJdSkills);
		inflearnJdSkillRepository.deleteAll(existingJdSkills);
	}

	private void createInflearnCourses(Skill skill, List<InflearnCourse> inflearnCourseList,
		List<WantedJdSkill> wantedJdSkillList) {
		for (InflearnCourse inflearnCourse : inflearnCourseList) {
			if (!courseDuplicationCheckerService.isDuplicate(inflearnCourse.getCourseId())) {
				InflearnCourse savedCourse = inflearnCourseRepository.save(inflearnCourse);
				wantedJdSkillList.forEach(wantedJdSkill -> createInflearnJdSkill(savedCourse, wantedJdSkill));
			}
		}
	}

	private void createInflearnJdSkill(InflearnCourse course, WantedJdSkill wantedJdSkill) {
		System.out.println("wantedJdSkill: " + wantedJdSkill);
		System.out.println("course: " + course);
		InflearnJdSkill createdJdSkill = EntityConverter.createInflearnJdSkill(course, wantedJdSkill);
		inflearnJdSkillRepository.save(createdJdSkill);
	}
}
