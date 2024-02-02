package kernel.jdon.coffeechat.dto.request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class CreateCoffeeChatRequest {

	private String title;
	private String content;
	private Long totalRecruitCount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
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
