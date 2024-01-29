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
public class FindCoffeeChatListResponse {

	private Long coffeeChatId;
	private String nickname;
	private String job;
	private String title;
	private String status;
	private boolean isDeleted;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private LocalDateTime meetDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private LocalDateTime createdDate;
	private Long totalRecruitCount;
	private Long currentRecruitCount;

	public static FindCoffeeChatListResponse of(CoffeeChat coffeeChat) {
		return FindCoffeeChatListResponse.builder()
			.coffeeChatId(coffeeChat.getId())
			.nickname(coffeeChat.getMember().getNickname())
			.job((coffeeChat.getMember().getJobCategory().getName()))
			.title(coffeeChat.getTitle())
			.status(coffeeChat.getCoffeeChatStatus().getActiveStatus())
			.isDeleted(coffeeChat.isDeleted())
			.meetDate(coffeeChat.getMeetDate())
			.createdDate(coffeeChat.getCreatedDate())
			.totalRecruitCount(coffeeChat.getTotalRecruitCount())
			.currentRecruitCount(coffeeChat.getCurrentRecruitCount())
			.build();

	}

}
