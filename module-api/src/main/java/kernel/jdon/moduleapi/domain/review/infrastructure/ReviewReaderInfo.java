package kernel.jdon.moduleapi.domain.review.infrastructure;

import java.time.LocalDateTime;

import com.querydsl.core.annotations.QueryProjection;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewReaderInfo {

    @Getter
    public static class FindReview {
        private final Long id;
        private final String content;
        private final String nickname;
        private final LocalDateTime createdDate;
        private final Long memberId;

        @QueryProjection
        public FindReview(Long id, String content, String nickname, LocalDateTime createdDate,
            Long memberId) {
            this.id = id;
            this.content = content;
            this.nickname = nickname;
            this.createdDate = createdDate;
            this.memberId = memberId;
        }
    }

}
