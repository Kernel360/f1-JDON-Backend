package kernel.jdon.coffeechat.dto.response;

import java.time.LocalDateTime;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.coffeechat.domain.CoffeeChatActiveStatus;
import kernel.jdon.faq.dto.response.FindFaqResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class FindCoffeeChatResponse {

	private Long coffeeChatId;
	private String nickname;
	private String title;
	private String content;
	private Long viewCount;
	private String status;
	private LocalDateTime meetDate;
	private String openChatUrl;
	private Long totalRecruitCount;
	private Long currentRecruitCount;

	public static FindCoffeeChatResponse of(CoffeeChat coffeeChat) {
		return FindCoffeeChatResponse.builder()
			.coffeeChatId(coffeeChat.getId())
			.nickname(coffeeChat.getMember().getNickname())
			.title(coffeeChat.getTitle())
			.content(coffeeChat.getContent())
			.viewCount(coffeeChat.getViewCount())
			.status(coffeeChat.getCoffeeChatStatus().getActiveStatus())
			.meetDate(coffeeChat.getMeetDate())
			.openChatUrl(coffeeChat.getOpenChatUrl())
			.totalRecruitCount(coffeeChat.getTotalRecruitCount())
			.currentRecruitCount(coffeeChat.getCurrentRecruitCount())
			.build();

	}

}
