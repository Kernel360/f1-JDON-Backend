package kernel.jdon.coffeechat.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@AllArgsConstructor
public class FindCoffeeChatResponse {

	private Long coffeeChatId;
	private String nickname;
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
	private String job;

	public static FindCoffeeChatResponse of(CoffeeChat coffeeChat) {
		return FindCoffeeChatResponse.builder()
			.coffeeChatId(coffeeChat.getId())
			.nickname(coffeeChat.getMember().getNickname())
			.title(coffeeChat.getTitle())
			.content(coffeeChat.getContent())
			.viewCount(coffeeChat.getViewCount())
			.status(coffeeChat.getCoffeeChatStatus().getActiveStatus())
			.meetDate(coffeeChat.getMeetDate())
			.createdDate(coffeeChat.getCreatedDate())
			.openChatUrl(coffeeChat.getOpenChatUrl())
			.totalRecruitCount(coffeeChat.getTotalRecruitCount())
			.currentRecruitCount(coffeeChat.getCurrentRecruitCount())
			.build();

	}

}
