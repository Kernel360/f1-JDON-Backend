package kernel.jdon.coffeechat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteCoffeeChatResponse {

	private Long coffeeChatId;

	public static DeleteCoffeeChatResponse of(Long coffeeChatId) {
		return new DeleteCoffeeChatResponse(coffeeChatId);
	}

}
