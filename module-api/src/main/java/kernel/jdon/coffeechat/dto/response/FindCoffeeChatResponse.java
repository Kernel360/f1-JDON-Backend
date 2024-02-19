package kernel.jdon.coffeechat.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FindCoffeeChatResponse {

	private Long coffeeChatId;
	private Long hostId;
	private Boolean isAuthor;
	private String nickname;
	private String job;
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

	public static FindCoffeeChatResponse of(CoffeeChat coffeeChat, Boolean isAuthor) {
		return FindCoffeeChatResponse.builder()
			.coffeeChatId(coffeeChat.getId())
			.hostId(coffeeChat.getMember().getId())
			.isAuthor(isAuthor)
			.nickname(coffeeChat.getMember().getNickname())
			.job(coffeeChat.getMember().getJobCategory().getName())
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
