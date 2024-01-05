package kernel.jdon.coffeechat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.coffeechat.dto.request.CreateCoffeeChatRequest;
import kernel.jdon.coffeechat.dto.response.CreateCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.FindCoffeeChatResponse;
import kernel.jdon.coffeechat.repository.CoffeeChatRepository;
import kernel.jdon.error.code.CoffeeChatErrorCode;
import kernel.jdon.error.exception.api.ApiException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CoffeeChatService {

	private final CoffeeChatRepository coffeeChatRepository;

	@Transactional
	public FindCoffeeChatResponse find(Long coffeeChatId) {
		CoffeeChat coffeeChat = coffeeChatRepository.findById(coffeeChatId)
			.orElseThrow(() -> new ApiException(CoffeeChatErrorCode.NOT_FOUND_COFFEECHAT));
		coffeeChat.increaseViewCount();

		return FindCoffeeChatResponse.of(coffeeChat);
	}

	@Transactional
	public CreateCoffeeChatResponse create(CreateCoffeeChatRequest request) {
		CoffeeChat savedCoffeeChat = coffeeChatRepository.save(CreateCoffeeChatRequest.toEntity(request));

		return CreateCoffeeChatResponse.of(savedCoffeeChat.getId());
	}

}
