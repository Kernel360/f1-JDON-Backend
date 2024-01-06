package kernel.jdon.coffeechat.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreateCoffeeChatResponse {
	private Long coffeeChatId;

	public static CreateCoffeeChatResponse of(Long coffeeChatId) {
		return new CreateCoffeeChatResponse(coffeeChatId);
	}
}
