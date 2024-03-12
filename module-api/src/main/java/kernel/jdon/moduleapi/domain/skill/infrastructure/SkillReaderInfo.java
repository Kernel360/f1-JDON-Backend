package kernel.jdon.moduleapi.domain.skill.infrastructure;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SkillReaderInfo {

    @Getter
    public static class FindHotSkill {
        private Long id;
        private String keyword;

        @QueryProjection
        public FindHotSkill(Long id, String keyword) {
            this.id = id;
            this.keyword = keyword;
        }
    }

    @Getter
    public static class FindMemberSkill {
        private Long skillId;
        private String keyword;

        @QueryProjection
        public FindMemberSkill(Long skillId, String keyword) {
            this.skillId = skillId;
            this.keyword = keyword;
        }
    }

    @Getter
    public static class FindWantedJd {
        private Long id;
        private String company;
        private String title;
        private String imageUrl;
        private String jdUrl;

        @QueryProjection
        public FindWantedJd(Long id, String company, String title, String imageUrl, String jdUrl) {
            this.id = id;
            this.company = company;
            this.title = title;
            this.imageUrl = imageUrl;
            this.jdUrl = jdUrl;
        }
    }

    @Getter
    public static class FindInflearnLecture {
        private Long lectureId;
        private String title;
        private String lectureUrl;
        private String imageUrl;
        private String instructor;
        private Long studentCount;
        private Integer price;
        private Boolean isFavorite;

        @QueryProjection
        public FindInflearnLecture(Long lectureId, String title, String lectureUrl, String imageUrl, String instructor,
            Long studentCount, Integer price, boolean isFavorite) {
            this.lectureId = lectureId;
            this.title = title;
            this.lectureUrl = lectureUrl;
            this.imageUrl = imageUrl;
            this.instructor = instructor;
            this.studentCount = studentCount;
            this.price = price;
            this.isFavorite = isFavorite;
        }
    }
}
