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
import kernel.jdon.coffeechat.error.CoffeeChatErrorCode;
import kernel.jdon.coffeechat.repository.CoffeeChatRepository;
import kernel.jdon.global.exception.ApiException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CoffeeChatService {

	private final CoffeeChatRepository coffeeChatRepository;

	private CoffeeChat findByIdIfNotDeleted(Long coffeeChatId) {

		return coffeeChatRepository.findByIdAndIsDeletedFalse(coffeeChatId)
			.orElseThrow(() -> new ApiException(CoffeeChatErrorCode.NOT_FOUND_COFFEECHAT));
	}

	@Transactional
	public FindCoffeeChatResponse find(Long coffeeChatId) {
		CoffeeChat findCoffeeChat = findByIdIfNotDeleted(coffeeChatId);
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
		CoffeeChat findCoffeeChat = findByIdIfNotDeleted(coffeeChatId);
		CoffeeChat target = UpdateCoffeeChatRequest.toEntity(request);

		validate(findCoffeeChat, target);

		findCoffeeChat.updateCoffeeChat(target);

		return UpdateCoffeeChatResponse.of(findCoffeeChat.getId());
	}

	private void validate(CoffeeChat findCoffeeChat, CoffeeChat target) {
		validateRecruitCount(findCoffeeChat, target);
		// todo 추가되는 로직들 ...
	};
	private void validateRecruitCount(CoffeeChat findCoffeeChat, CoffeeChat target) {
		if (findCoffeeChat.isValidRecruitCount(target.getTotalRecruitCount())) {
			throw new ApiException(CoffeeChatErrorCode.EXPIRED_COFFEECHAT);
		}
	}

	// todo : 서비스에서 처리한다면 이런식으로 불러서 쓸 것
	private void validateMeetDate(CoffeeChat findCoffeeChat, CoffeeChat target) {
		// if () {
		//
		// }
		//
		// if () {
		//
		// }
	}

	@Transactional
	public DeleteCoffeeChatResponse delete(Long coffeeChatId) {
		CoffeeChat findCoffeeChat = findByIdIfNotDeleted(coffeeChatId);
		coffeeChatRepository.deleteById(findCoffeeChat.getId());

		return DeleteCoffeeChatResponse.of(coffeeChatId);
	}

}
