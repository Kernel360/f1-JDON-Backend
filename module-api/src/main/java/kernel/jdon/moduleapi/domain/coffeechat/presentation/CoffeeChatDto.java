package kernel.jdon.moduleapi.domain.coffeechat.presentation;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import kernel.jdon.moduleapi.global.page.CustomPageInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoffeeChatDto {
    @Getter
    @Builder
    public static class FindCoffeeChatResponse {
        private Long coffeeChatId;
        private Long hostId;
        private String nickname;
        private String hostJobCategoryName;
        private String title;
        private String content;
        private Long viewCount;
        private String status;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime meetDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime createdDate;
        private String openChatUrl;
        private Long totalRecruitCount;
        private Long currentRecruitCount;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateCoffeeChatRequest {
        private String title;
        private String content;
        private Long totalRecruitCount;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime meetDate;
        private String openChatUrl;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateCoffeeChatRequest {
        private String title;
        private String content;
        private Long totalRecruitCount;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime meetDate;
        private String openChatUrl;
    }

    @Getter
    @Builder
    public static class FindCoffeeChatListResponse {
        private List<FindCoffeeChat> content;
        private CustomPageInfo pageInfo;
    }

    @Getter
    @Builder
    public static class FindCoffeeChat {
        private Long coffeeChatId;
        private String nickname;
        private String hostJobCategoryName;
        private String title;
        private String activeStatus;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Boolean isDeleted;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime meetDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime createdDate;
        private Long totalRecruitCount;
        private Long currentRecruitCount;
    }
}
