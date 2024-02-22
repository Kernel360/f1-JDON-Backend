package kernel.jdon.moduleapi.domain.coffeechat.core;

import java.time.LocalDateTime;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoffeeChatCommand {

	@Getter
	@Builder
	public static class FindCoffeeChatListRequest {
		private CoffeeChatSortCondition sort;
		private String keyword;
		private Long jobCategory;
	}

    @Getter
    @Builder
    public static class CreateRequest {

        private String title;
        private String content;
        private Long totalRecruitCount;
        private LocalDateTime meetDate;
        private String openChatUrl;

        public CoffeeChat toEntity(Member member) {
            return CoffeeChat.builder()
                .title(title)
                .content(content)
                .totalRecruitCount(totalRecruitCount)
                .meetDate(meetDate)
                .openChatUrl(openChatUrl)
                .member(member)
                .build();
        }

    }

    @Getter
    @Builder
    public static class UpdateRequest {
        private String title;
        private String content;
        private Long totalRecruitCount;
        private LocalDateTime meetDate;
        private String openChatUrl;

        public CoffeeChat toEntity() {
            return CoffeeChat.builder()
                .title(title)
                .content(content)
                .totalRecruitCount(totalRecruitCount)
                .meetDate(meetDate)
                .openChatUrl(openChatUrl)
                .build();
        }
    }
}
