package kernel.jdon.coffeechat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCoffeeChatResponse {
	private Long coffeeChatId;

	public static CreateCoffeeChatResponse of(Long coffeeChatId) {
		return new CreateCoffeeChatResponse(coffeeChatId);
	}
}
