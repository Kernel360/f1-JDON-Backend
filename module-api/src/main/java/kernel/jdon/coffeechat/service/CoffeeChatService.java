package kernel.jdon.coffeechat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.coffeechat.dto.request.CreateCoffeeChatRequest;
import kernel.jdon.coffeechat.dto.request.UpdateCoffeeChatRequest;
import kernel.jdon.coffeechat.dto.response.CreateCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.DeleteCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.FindCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.UpdateCoffeeChatResponse;
import kernel.jdon.coffeechat.repository.CoffeeChatRepository;
import kernel.jdon.error.code.api.CoffeeChatErrorCode;
import kernel.jdon.error.exception.api.ApiException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CoffeeChatService {

	private final CoffeeChatRepository coffeeChatRepository;

	private CoffeeChat findById(Long coffeeChatId) {

		return coffeeChatRepository.findById(coffeeChatId)
			.orElseThrow(() -> new ApiException(CoffeeChatErrorCode.NOT_FOUND_COFFEECHAT));
	}

	@Transactional
	public FindCoffeeChatResponse find(Long coffeeChatId) {
		CoffeeChat findCoffeeChat = findById(coffeeChatId);
		increaseViewCount(findCoffeeChat);

		return FindCoffeeChatResponse.of(findCoffeeChat);
	}

	private void increaseViewCount(CoffeeChat coffeeChat) {
		coffeeChat.increaseViewCount();
	}

	@Transactional
	public CreateCoffeeChatResponse create(CreateCoffeeChatRequest request) {
		CoffeeChat savedCoffeeChat = coffeeChatRepository.save(CreateCoffeeChatRequest.toEntity(request));

		return CreateCoffeeChatResponse.of(savedCoffeeChat.getId());
	}

	@Transactional
	public UpdateCoffeeChatResponse update(Long coffeeChatId, UpdateCoffeeChatRequest request) {
		CoffeeChat findCoffeeChat = findById(coffeeChatId);
		findCoffeeChat.updateCoffeeChat(request);

		return UpdateCoffeeChatResponse.of(findCoffeeChat.getId());
	}

	@Transactional
	public DeleteCoffeeChatResponse delete(Long coffeeChatId) {
		CoffeeChat findCoffeeChat = findById(coffeeChatId);
		coffeeChatRepository.deleteById(findCoffeeChat.getId());
		
		return DeleteCoffeeChatResponse.of(coffeeChatId);
	}

}
