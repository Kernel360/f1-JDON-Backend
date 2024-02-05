package kernel.jdon.coffeechat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import kernel.jdon.coffeechat.dto.request.CoffeeChatCondition;
import kernel.jdon.coffeechat.dto.response.FindCoffeeChatListResponse;

public interface CustomCoffeeChatRepository {
	Page<FindCoffeeChatListResponse> findCoffeeChatList(Pageable pageable, CoffeeChatCondition coffeeChatCondition);
}
