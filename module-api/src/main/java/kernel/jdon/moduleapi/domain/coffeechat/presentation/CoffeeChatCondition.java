package kernel.jdon.moduleapi.domain.coffeechat.presentation;

import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatSortType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CoffeeChatCondition {
    private CoffeeChatSortType sort;
    private String keyword;
    private Long jobCategory;
}
