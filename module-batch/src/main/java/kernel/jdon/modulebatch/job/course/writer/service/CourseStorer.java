package kernel.jdon.modulebatch.job.course.writer.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.modulebatch.domain.inflearncourse.repository.InflearnCourseRepository;
import kernel.jdon.modulebatch.domain.inflearnjdskill.repository.InflearnJdSkillRepository;
import kernel.jdon.modulebatch.domain.skill.error.SkillErrorCode;
import kernel.jdon.modulebatch.domain.skill.repository.SkillRepository;
import kernel.jdon.modulebatch.global.exception.BatchException;
import kernel.jdon.modulebatch.job.course.reader.service.converter.EntityConverter;
import kernel.jdon.moduledomain.inflearncourse.domain.InflearnCourse;
import kernel.jdon.moduledomain.inflearnjdskill.domain.InflearnJdSkill;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.skill.domain.Skill;
import lombok.RequiredArgsConstructor;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CourseStorer {

    private final InflearnCourseRepository inflearnCourseRepository;
    private final InflearnJdSkillRepository inflearnJdSkillRepository;
    private final SkillRepository skillRepository;

    @Transactional
    public void createInflearnCourseAndInflearnJdSkill(String skillKeyword, List<InflearnCourse> newCourseList) {
        List<Long> jobCategoryIdList = findJobCategoryIds(skillKeyword);

        for (Long jobCategoryId : jobCategoryIdList) {
            Skill findSkill = skillRepository.findByJobCategoryIdAndKeyword(jobCategoryId, skillKeyword)
                .orElseThrow(() -> new BatchException(SkillErrorCode.NOT_FOUND_SKILL));
            deleteExistingInflearnJdSkills(findSkill);
            createOrUpdateCourseList(newCourseList, findSkill);
        }
    }

    public List<Long> findJobCategoryIds(String skillKeyword) {
        List<Skill> skills = skillRepository.findByKeyword(skillKeyword);
        return skills.stream()
            .map(Skill::getJobCategory)
            .map(JobCategory::getId)
            .distinct()
            .toList();
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
        return inflearnCourseRepository.findByCourseId(course.getCourseId())
            .orElseGet(() -> inflearnCourseRepository.save(course));
    }

    private void createInflearnJdSkill(InflearnCourse course, Skill skill) {
        InflearnJdSkill createJdSkill = EntityConverter.createInflearnJdSkill(course, skill);
        inflearnJdSkillRepository.save(createJdSkill);
    }
}
