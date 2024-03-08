package kernel.jdon.moduleapi.domain.jd.infrastructure;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JdReaderInfo {
    @Getter
    public static class FindWantedJd {
        private final Long id;
        private final String title;
        private final String company;
        private final String imageUrl;
        private final String jobCategoryName;

        @QueryProjection
        public FindWantedJd(Long id, String title, String company, String imageUrl, String jobCategoryName) {
            this.id = id;
            this.title = title;
            this.company = company;
            this.imageUrl = imageUrl;
            this.jobCategoryName = jobCategoryName;
        }
    }
}
