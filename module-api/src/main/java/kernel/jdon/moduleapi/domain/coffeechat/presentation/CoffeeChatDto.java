package kernel.jdon.moduleapi.domain.coffeechat.presentation;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    public static class CancelCoffeeChatResponse {
        private final Long coffeeChatId;
    }

    @Getter
    @Builder
    public static class UpdateCoffeeChatResponse {
        private final Long coffeeChatId;
    }

    @Getter
    @Builder
    public static class CreateCoffeeChatResponse {
        private final Long coffeeChatId;
    }

    @Getter
    @Builder
    public static class ApplyCoffeeChatResponse {
        private final Long coffeeChatId;
    }

    @Getter
    @Builder
    public static class DeleteCoffeeChatResponse {
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
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private final LocalDateTime meetDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private final LocalDateTime createdDate;
        private final String openChatUrl;
        private final Long totalRecruitCount;
        private final Long currentRecruitCount;

        public boolean getIsParticipant() {
            return isParticipant;
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateCoffeeChatRequest {
        @NotBlank(message = "제목은 필수 입력 항목 입니다.")
        @Size(min = 10, max = 50, message = "제목은 10자 이상 50자 이하로 작성해주세요.")
        private String title;
        @NotBlank(message = "내용은 필수 입력 항목 입니다.")
        @Size(min = 50, max = 500, message = "내용은 50자 이상 500자 이하로 작성해주세요.")
        private String content;
        @NotNull(message = "모집인원은 필수 입력 항목 입니다.")
        @Range(min = 1, max = 100, message = "모집인원은 1명 이상 100명 이하로 설정해주세요.")
        private Long totalRecruitCount;
        @Future(message = "일시는 과거 시점으로 설정할 수 없습니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime meetDate;
        @NotBlank(message = "오픈채팅 링크는 필수 입력 항목 입니다.")
        @Pattern(regexp = "https:\\/\\/open\\.kakao\\.com\\/o\\/[A-Za-z0-9]{8}$", message = "URL 형식이 올바르지 않습니다.")
        private String openChatUrl;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateCoffeeChatRequest {
        @NotBlank(message = "제목은 필수 입력 항목 입니다.")
        @Size(min = 10, max = 50, message = "제목은 10자 이상 50자 이하로 작성해주세요.")
        private String title;
        @NotBlank(message = "내용은 필수 입력 항목 입니다.")
        @Size(min = 50, max = 500, message = "내용은 50자 이상 500자 이하로 작성해주세요.")
        private String content;
        @NotNull(message = "모집인원은 필수 입력 항목 입니다.")
        @Range(min = 1, max = 100, message = "모집인원은 1명 이상 100명 이하로 설정해주세요.")
        private Long totalRecruitCount;
        @Future(message = "일시는 과거 시점으로 설정할 수 없습니다.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private LocalDateTime meetDate;
        @NotBlank(message = "오픈채팅 링크는 필수 입력 항목 입니다.")
        @Pattern(regexp = "https:\\/\\/open\\.kakao\\.com\\/o\\/[A-Za-z0-9]{8}$", message = "URL 형식이 올바르지 않습니다.")
        private String openChatUrl;
    }

    @Getter
    @Builder
    public static class FindCoffeeChatListResponse {
        private final List<FindCoffeeChat> content;
        private final CustomPageInfo pageInfo;
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
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private final LocalDateTime meetDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        private final LocalDateTime createdDate;
        private final Long totalRecruitCount;
        private final Long currentRecruitCount;
        private final Long viewCount;
    }
}
