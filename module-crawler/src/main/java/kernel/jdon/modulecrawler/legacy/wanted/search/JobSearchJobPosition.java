package kernel.jdon.modulecrawler.legacy.wanted.search;

import java.util.Arrays;
import java.util.List;

import kernel.jdon.modulecrawler.legacy.search.SearchCondition;
import kernel.jdon.modulecrawler.legacy.wanted.skill.BackendSkillType;
import kernel.jdon.modulecrawler.legacy.wanted.skill.FrontendSkillType;
import kernel.jdon.modulecrawler.legacy.wanted.skill.SkillType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum JobSearchJobPosition implements SearchCondition {
    JOB_POSITION_FRONTEND("669", "프론트엔드 개발자", Arrays.asList(FrontendSkillType.values())),
    JOB_POSITION_SERVER("872", "서버 개발자", Arrays.asList(BackendSkillType.values()));

    public final static String SEARCH_KEY = "job_ids";
    private final String searchValue;
    private final String description;
    private final List<SkillType> skillTypeList;

    public static JobSearchJobPosition[] getAllPositions() {
        return values();
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getSearchValue() {
        return this.searchValue;
    }

    public List<SkillType> getSkillTypeList() {
        return this.skillTypeList;
    }
}
