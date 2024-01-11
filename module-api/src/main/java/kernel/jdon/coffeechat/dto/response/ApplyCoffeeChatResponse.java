package kernel.jdon.coffeechat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyCoffeeChatResponse {

	private Long coffeeChatId;

	public static ApplyCoffeeChatResponse of(Long coffeeChatId) {
		return new ApplyCoffeeChatResponse(coffeeChatId);
	}
}
