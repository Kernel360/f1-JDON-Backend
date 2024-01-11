package kernel.jdon.coffeechat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateCoffeeChatResponse {

	private Long coffeeChatId;

	public static UpdateCoffeeChatResponse of(Long coffeeChatId) {
		return new UpdateCoffeeChatResponse(coffeeChatId);
	}
}
