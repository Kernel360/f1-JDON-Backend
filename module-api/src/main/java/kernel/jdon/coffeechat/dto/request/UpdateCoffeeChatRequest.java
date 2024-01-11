package kernel.jdon.coffeechat.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateCoffeeChatRequest {

	private String title;
	private String content;
	private Long totalRecruitCount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
	private LocalDateTime meetDate;
	private String openChatUrl;

	public static CoffeeChat toEntity(UpdateCoffeeChatRequest request) {
		return CoffeeChat.builder()
			.title(request.title)
			.content(request.content)
			.totalRecruitCount(request.totalRecruitCount)
			.meetDate(request.meetDate)
			.openChatUrl(request.openChatUrl)
			.build();
	}
}

