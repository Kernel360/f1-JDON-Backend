package kernel.jdon.crawler.inflearn.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import kernel.jdon.crawler.common.repository.SkillRepository;
import kernel.jdon.crawler.common.repository.WantedJdSkillRepository;
import kernel.jdon.crawler.inflearn.converter.EntityConverter;
import kernel.jdon.crawler.inflearn.repository.InflearnCourseRepository;
import kernel.jdon.crawler.inflearn.repository.InflearnJdSkillRepository;
import kernel.jdon.inflearn.domain.InflearnCourse;
import kernel.jdon.inflearnJdskill.domain.InflearnJdSkill;
import kernel.jdon.skill.domain.Skill;
import kernel.jdon.wantedskill.domain.WantedJdSkill;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseStorageService {

	private final InflearnCourseRepository inflearnCourseRepository;
	private final InflearnJdSkillRepository inflearnJdSkillRepository;
	private final WantedJdSkillRepository wantedJdSkillRepository;
	private final SkillRepository skillRepository;

	protected void createInflearnCourseAndInflearnJdSkill(InflearnCourse inflearnCourse, String skillTags) {
		if (shouldCreateInflearnCourse(skillTags)) {
			InflearnCourse savedCourse = inflearnCourseRepository.save(inflearnCourse);
			processAndCreateInflearnJdSKill(savedCourse, skillTags);
		}
	}

	private boolean shouldCreateInflearnCourse(String skillTags) {
		String[] skillList = skillTags.split(", ");
		for (String skill : skillList) {
			Optional<Skill> existingSkill = skillRepository.findByKeyword(skill);
			if (existingSkill.isPresent()) {
				List<WantedJdSkill> wantedJdSkills = wantedJdSkillRepository.findBySkill(existingSkill.get());
				if (!wantedJdSkills.isEmpty()) {
					return true;
				}
			}
		}

		return false;
	}

	private void processAndCreateInflearnJdSKill(InflearnCourse inflearnCourse, String skillTags) {
		String[] skillList = skillTags.split(", ");
		for (String skill : skillList) {
			associateWantedJdSkillWithInflearnCourse(inflearnCourse, skill);
		}
	}

	private void associateWantedJdSkillWithInflearnCourse(InflearnCourse inflearnCourse, String skill) {
		List<WantedJdSkill> wantedJdSkillList = findWantedJdSkill(skill);
		wantedJdSkillList.forEach(wantedJdSkill -> createInflearnJdSkillIfNotExists(inflearnCourse, wantedJdSkill));
	}

	private List<WantedJdSkill> findWantedJdSkill(String keyword) {
		Optional<Skill> existingSkill = skillRepository.findByKeyword(keyword);
		if (existingSkill.isPresent()) {
			return wantedJdSkillRepository.findBySkill(existingSkill.get());
		}

		return new ArrayList<>();
	}

	private void createInflearnJdSkillIfNotExists(InflearnCourse inflearnCourse, WantedJdSkill wantedJdSkill) {
		if (!inflearnJdSkillRepository.existsByInflearnCourseAndWantedJdSkill(inflearnCourse, wantedJdSkill)) {
			InflearnJdSkill inflearnJdSkill = EntityConverter.createInflearnJdSkill(inflearnCourse, wantedJdSkill);
			inflearnJdSkillRepository.save(inflearnJdSkill);
		}
	}
}
