package kernel.jdon.coffeechat.dto.request;

import java.time.LocalDateTime;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.faq.dto.request.CreateFaqRequest;
import kernel.jdon.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCoffeeChatRequest {

	private String title;
	private String content;
	private Long totalRecruitCount;
	private LocalDateTime meetDate;
	private String openChatUrl;

	public static CoffeeChat toEntity(CreateCoffeeChatRequest request) {
		return CoffeeChat.builder()
			.title(request.getTitle())
			.content(request.getContent())
			.totalRecruitCount(request.getTotalRecruitCount())
			.meetDate(request.getMeetDate())
			.openChatUrl(request.getOpenChatUrl())
			.member(Member.builder().id(1L).build())
			.build();
	}

}
