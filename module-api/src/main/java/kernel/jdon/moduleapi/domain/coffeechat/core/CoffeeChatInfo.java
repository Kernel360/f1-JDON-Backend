package kernel.jdon.moduleapi.domain.coffeechat.core;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import kernel.jdon.moduleapi.domain.coffeechat.infrastructure.CoffeeChatReaderInfo;
import kernel.jdon.moduleapi.global.page.CustomPageInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoffeeChatInfo {

    @Getter
    @Builder
    public static class UpdatedCoffeeChatResponse {
        private final Long coffeeChatId;
    }

    @Getter
    @Builder
    public static class CreatedCoffeeChatResponse {
        private final Long coffeeChatId;
    }

    @Getter
    @Builder
    public static class AppliedCoffeeChatResponse {
        private final Long coffeeChatId;
    }

    @Getter
    @Builder
    public static class DeletedCoffeeChatResponse {
        private final Long coffeeChatId;
    }

    @Getter
    @Builder
    public static class FindCoffeeChatResponse {
        private final Long coffeeChatId;
        private final Long hostId;
        private final boolean isParticipant;
        private final String nickname;
        private final String hostJobCategoryName;
        private final String title;
        private final String content;
        private final Long viewCount;
        private final String status;
        private final LocalDateTime meetDate;
        private final LocalDateTime createdDate;
        private final String openChatUrl;
        private final Long totalRecruitCount;
        private final Long currentRecruitCount;

        public boolean getIsParticipant() {
            return isParticipant;
        }
    }

    @Getter
    public static class FindCoffeeChatListResponse {
        private final List<FindCoffeeChat> content;
        private final CustomPageInfo pageInfo;

        public FindCoffeeChatListResponse(List<FindCoffeeChat> content, CustomPageInfo pageInfo) {
            this.content = content;
            this.pageInfo = pageInfo;
        }
    }

    @Getter
    @Builder
    public static class FindCoffeeChat {
        private final Long coffeeChatId;
        private final String nickname;
        private final String hostJobCategoryName;
        private final String title;
        private final String activeStatus;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final Boolean isDeleted;
        private final LocalDateTime meetDate;
        private final LocalDateTime createdDate;
        private final Long totalRecruitCount;
        private final Long currentRecruitCount;
        private final Long viewCount;

        public static FindCoffeeChat of(CoffeeChatReaderInfo.FindCoffeeChatListResponse readerInfo) {
            return FindCoffeeChat.builder()
                .coffeeChatId(readerInfo.getCoffeeChatId())
                .nickname(readerInfo.getNickname())
                .hostJobCategoryName(readerInfo.getHostJobCategoryName())
                .title(readerInfo.getTitle())
                .activeStatus(readerInfo.getActiveStatus())
                .meetDate(readerInfo.getMeetDate())
                .createdDate(readerInfo.getCreatedDate())
                .totalRecruitCount(readerInfo.getTotalRecruitCount())
                .currentRecruitCount(readerInfo.getCurrentRecruitCount())
                .viewCount(readerInfo.getViewCount())
                .build();
        }
    }

    @Getter
    @Builder
    public static class CanceledCoffeeChatResponse {
        private final Long coffeeChatId;
    }
}
