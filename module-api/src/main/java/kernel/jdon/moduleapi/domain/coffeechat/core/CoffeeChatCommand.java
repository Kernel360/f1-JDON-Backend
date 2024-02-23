package kernel.jdon.moduleapi.domain.coffeechat.core;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoffeeChatCommand {

	@Getter
	@Builder
	public static class FindCoffeeChatListRequest {
		private CoffeeChatSortCondition sort;
		private String keyword;
		private Long jobCategory;
	}

}
