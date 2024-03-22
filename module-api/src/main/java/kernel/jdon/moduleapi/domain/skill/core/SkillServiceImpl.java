package kernel.jdon.moduleapi.domain.skill.core;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryReader;
import kernel.jdon.moduleapi.domain.skill.core.inflearnjd.InflearnJdSkillReader;
import kernel.jdon.moduleapi.domain.skill.core.wantedjd.WantedJdSkillReader;
import kernel.jdon.moduleapi.domain.skill.infrastructure.keyword.SkillKeywordCache;
import kernel.jdon.moduledomain.jobcategory.domain.JobCategory;
import kernel.jdon.moduledomain.skill.domain.SkillType;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SkillServiceImpl implements SkillService {
    private final SkillReader skillReader;
    private final WantedJdSkillReader wantedJdSkillReader;
    private final InflearnJdSkillReader inflearnJdSkillReader;
    private final JobCategoryReader jobCategoryReader;
    private final SkillKeywordCache skillKeywordCache;

    @Override
    public SkillInfo.FindHotSkillListResponse getHotSkillList() {
        final List<SkillInfo.FindHotSkill> hotSkillList = skillReader.findHotSkillList();

        return new SkillInfo.FindHotSkillListResponse(hotSkillList);
    }

    @Override
    public SkillInfo.FindMemberSkillListResponse getMemberSkillList(final Long memberId) {
        final List<SkillInfo.FindMemberSkill> memberSkillList = skillReader.findMemberSkillList(memberId);

        return new SkillInfo.FindMemberSkillListResponse(memberSkillList);
    }

    @Override
    public SkillInfo.FindJobCategorySkillListResponse getJobCategorySkillList(final Long jobCategoryId) {
        final JobCategory findJobCategory = jobCategoryReader.findById(jobCategoryId);
        final List<SkillInfo.FindJobCategorySkill> jobCategorySkillList = findJobCategory.getSkillList().stream()
            .filter(skill -> !skill.getKeyword().equals(SkillType.getOrderKeyword()))
            .map(skill -> new SkillInfo.FindJobCategorySkill(skill.getId(), skill.getKeyword()))
            .toList();

        return new SkillInfo.FindJobCategorySkillListResponse(jobCategorySkillList);
    }

    @Override
    public SkillInfo.FindDataListBySkillResponse getDataListBySkill(final String relatedKeyword, final Long memberId) {
        String searchKeyword = Optional.ofNullable(relatedKeyword)
            .map(String::trim)
            .filter(keyword -> !keyword.isEmpty())
            .orElseGet(this::getHotSkillKeyword);

        final List<String> findOriginSkillKeywordList = skillKeywordCache.findAssociatedKeywords(searchKeyword);
        List<SkillInfo.FindJd> findJdList = Collections.emptyList();
        List<SkillInfo.FindLecture> findLectureList = Collections.emptyList();
        if (!findOriginSkillKeywordList.isEmpty()) {
            findJdList = wantedJdSkillReader.findWantedJdListBySkill(findOriginSkillKeywordList);
            findLectureList = inflearnJdSkillReader.findInflearnLectureListBySkill(findOriginSkillKeywordList,
                memberId);
        }

        return new SkillInfo.FindDataListBySkillResponse(searchKeyword, findLectureList, findJdList);
    }

    private String getHotSkillKeyword() {
        return skillReader.findHotSkillList().get(0).getKeyword();
    }
}
