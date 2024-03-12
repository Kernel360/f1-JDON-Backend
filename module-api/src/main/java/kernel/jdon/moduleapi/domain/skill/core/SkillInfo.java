package kernel.jdon.moduleapi.domain.skill.core;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SkillInfo {

    @Getter
    @Builder
    public static class FindHotSkillListResponse {
        private final List<FindHotSkill> skillList;
    }

    @Getter
    @Builder
    public static class FindHotSkill {
        private final Long id;
        private final String keyword;
    }

    @Getter
    @Builder
    public static class FindMemberSkillListResponse {
        private final List<FindMemberSkill> skillList;
    }

    @Getter
    @Builder
    public static class FindMemberSkill {
        private final Long id;
        private final String keyword;
    }

    @Getter
    @Builder
    public static class FindJobCategorySkillListResponse {
        private final List<FindJobCategorySkill> skillList;
    }

    @Getter
    @Builder
    public static class FindJobCategorySkill {
        private final Long skillId;
        private final String keyword;
    }

    @Getter
    @Builder
    public static class FindDataListBySkillResponse {
        private final String keyword;
        private final List<FindLecture> lectureList;
        private final List<FindJd> jdList;
    }

    @Getter
    @Builder
    public static class FindLecture {
        private final Long lectureId;
        private final String title;
        private final String lectureUrl;
        private final String imageUrl;
        private final String instructor;
        private final Long studentCount;
        private final Integer price;
        private final Boolean isFavorite;
    }

    @Getter
    @Builder
    public static class FindJd {
        private final Long id;
        private final String company;
        private final String title;
        private final String imageUrl;
        private final String jdUrl;
    }
}
