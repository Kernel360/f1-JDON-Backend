package kernel.jdon.moduleapi.domain.coffeechat.core;

import java.time.LocalDateTime;

import kernel.jdon.moduledomain.coffeechat.domain.CoffeeChat;
import kernel.jdon.moduledomain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoffeeChatCommand {

	@Getter
	@Builder
	public static class FindCoffeeChatListRequest {
		private final CoffeeChatSortType sort;
		private final String keyword;
		private final Long jobCategory;
	}

    @Getter
    @Builder
    public static class CreateCoffeeChatRequest {

        private final String title;
        private final String content;
        private final Long totalRecruitCount;
        private final LocalDateTime meetDate;
        private final String openChatUrl;

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
    public static class UpdateCoffeeChatRequest {
        private final String title;
        private final String content;
        private final Long totalRecruitCount;
        private final LocalDateTime meetDate;
        private final String openChatUrl;
        private final Long coffeeChatId;
        private final Long memberId;

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
