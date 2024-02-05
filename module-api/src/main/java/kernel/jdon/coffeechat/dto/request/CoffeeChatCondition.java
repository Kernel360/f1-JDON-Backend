package kernel.jdon.coffeechat.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoffeeChatCondition {
	private CoffeeChatSortCondition sort;
	private String keyword;
	private Long jobCategory;
}
