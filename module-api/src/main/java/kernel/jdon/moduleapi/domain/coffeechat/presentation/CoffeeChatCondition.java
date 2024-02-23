package kernel.jdon.moduleapi.domain.coffeechat.presentation;

import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatSortCondition;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoffeeChatCondition {
	private CoffeeChatSortCondition sort;
	private String keyword;
	private Long jobCategory;
}
