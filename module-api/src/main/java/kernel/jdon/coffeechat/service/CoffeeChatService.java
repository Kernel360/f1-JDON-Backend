package kernel.jdon.coffeechat.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.coffeechat.domain.CoffeeChatActiveStatus;
import kernel.jdon.coffeechat.dto.request.CreateCoffeeChatRequest;
import kernel.jdon.coffeechat.dto.request.UpdateCoffeeChatRequest;
import kernel.jdon.coffeechat.dto.response.ApplyCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.CreateCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.DeleteCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.FindCoffeeChatListResponse;
import kernel.jdon.coffeechat.dto.response.FindCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.UpdateCoffeeChatResponse;
import kernel.jdon.coffeechat.error.CoffeeChatErrorCode;
import kernel.jdon.coffeechat.repository.CoffeeChatRepository;
import kernel.jdon.global.exception.ApiException;
import kernel.jdon.global.page.CustomPageResponse;
import kernel.jdon.member.domain.Member;
import kernel.jdon.member.error.MemberErrorCode;
import kernel.jdon.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CoffeeChatService {

	private final CoffeeChatRepository coffeeChatRepository;
	private final MemberRepository memberRepository;

	private CoffeeChat findExistCoffeeChat(Long coffeeChatId) {

		return coffeeChatRepository.findByIdAndIsDeletedFalse(coffeeChatId)
			.orElseThrow(() -> new ApiException(CoffeeChatErrorCode.NOT_FOUND_COFFEECHAT));
	}

	private CoffeeChat findExistAndOpenCoffeeChat(Long coffeeChatId) {

		CoffeeChat findCoffeeChat = findExistCoffeeChat(coffeeChatId);
		if (findCoffeeChat.getCoffeeChatStatus() != CoffeeChatActiveStatus.OPEN) {
			throw new ApiException(CoffeeChatErrorCode.NOT_OPEN_COFFEECHAT);
		}

		return findCoffeeChat;
	}

	private Member findMember(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new ApiException(MemberErrorCode.NOT_FOUND_MEMBER));
	}

	@Transactional
	public FindCoffeeChatResponse find(Long coffeeChatId) {
		CoffeeChat findCoffeeChat = findExistCoffeeChat(coffeeChatId);
		increaseViewCount(findCoffeeChat);

		return FindCoffeeChatResponse.of(findCoffeeChat);
	}

	private void increaseViewCount(CoffeeChat coffeeChat) {
		coffeeChat.increaseViewCount();
	}

	public CustomPageResponse findHostCoffeeChatList(Long memberId, Pageable pageable) {
		Page<FindCoffeeChatListResponse> findCoffeeChatPage = coffeeChatRepository.findAllByMemberIdAndIsDeletedFalse(
				memberId, pageable)
			.map(FindCoffeeChatListResponse::of);

		return CustomPageResponse.of(findCoffeeChatPage);
	}

	@Transactional
	public CreateCoffeeChatResponse create(CreateCoffeeChatRequest request, Long memberId) {
		Member findMember = findMember(memberId);
		CoffeeChat savedCoffeeChat = coffeeChatRepository.save(request.toEntity(findMember));

		return CreateCoffeeChatResponse.of(savedCoffeeChat.getId());
	}

	@Transactional
	public ApplyCoffeeChatResponse apply(Long coffeeChatId, Long memberId) {
		CoffeeChat findCoffeeChat = findExistAndOpenCoffeeChat(coffeeChatId);
		Member findMember = findMember(memberId);

		checkIfMemberIsHost(findMember, findCoffeeChat);
		findCoffeeChat.addCoffeeChatMember(findMember);

		return ApplyCoffeeChatResponse.of(findCoffeeChat.getId());
	}

	private void checkIfMemberIsHost(Member findMember, CoffeeChat findCoffeeChat) {
		if (findMember.getId().equals(findCoffeeChat.getMember().getId())) {
			throw new ApiException(CoffeeChatErrorCode.CAN_NOT_JOIN_OWN_COFFEECHAT);
		}
	}

	@Transactional
	public DeleteCoffeeChatResponse delete(Long coffeeChatId) {
		CoffeeChat findCoffeeChat = findExistCoffeeChat(coffeeChatId);
		coffeeChatRepository.deleteById(findCoffeeChat.getId());

		return DeleteCoffeeChatResponse.of(coffeeChatId);
	}

	@Transactional
	public UpdateCoffeeChatResponse update(Long coffeeChatId, UpdateCoffeeChatRequest request) {
		CoffeeChat findCoffeeChat = findExistCoffeeChat(coffeeChatId);
		CoffeeChat target = UpdateCoffeeChatRequest.toEntity(request);

		validateUpdate(findCoffeeChat, target);
		findCoffeeChat.updateCoffeeChat(target);

		return UpdateCoffeeChatResponse.of(findCoffeeChat.getId());
	}

	private void validateUpdate(CoffeeChat findCoffeeChat, CoffeeChat target) {
		checkRecruitCount(findCoffeeChat, target);
		checkMeetDate(findCoffeeChat, target);

	}

	private void checkMeetDate(CoffeeChat findCoffeeChat, CoffeeChat target) {
		if (findCoffeeChat.isValidMeetDate(target.getMeetDate())) {
			throw new ApiException(CoffeeChatErrorCode.MEET_DATE_ISBEFORE_NOW);
		}
	}

	private void checkRecruitCount(CoffeeChat findCoffeeChat, CoffeeChat target) {
		if (findCoffeeChat.isValidRecruitCount(target.getTotalRecruitCount())) {
			throw new ApiException(CoffeeChatErrorCode.EXPIRED_COFFEECHAT);
		}
	}

}
