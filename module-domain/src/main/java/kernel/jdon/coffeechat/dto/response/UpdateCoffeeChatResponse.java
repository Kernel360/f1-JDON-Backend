package kernel.jdon.coffeechat.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateCoffeeChatResponse {

	private Long coffeeChatId;

	public static UpdateCoffeeChatResponse of(Long coffeeChatId) {
		return new UpdateCoffeeChatResponse(coffeeChatId);
	}
}
