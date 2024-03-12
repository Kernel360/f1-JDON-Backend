package kernel.jdon.moduleapi.domain.skill.infrastructure.wantedjd;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WantedJdSkillReaderInfo {

    @Getter
    public static class FindJd {
        private String company;
        private String title;
        private String imageUrl;
        private String jdUrl;

        @QueryProjection
        public FindJd(String company, String title, String imageUrl, String jdUrl) {
            this.company = company;
            this.title = title;
            this.imageUrl = imageUrl;
            this.jdUrl = jdUrl;
        }
    }
}
