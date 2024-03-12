package kernel.jdon.moduleapi.domain.favorite.infrastructure;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FavoriteReaderInfo {

    @Getter
    @AllArgsConstructor
    public static class FindFavoriteListResponse {
        private Long lectureId;
        private String title;
        private String lectureUrl;
        private String imageUrl;
        private String instructor;
        private Long studentCount;
        private Integer price;
    }
}
