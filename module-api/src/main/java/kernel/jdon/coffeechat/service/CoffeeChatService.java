package kernel.jdon.coffeechat.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.coffeechat.domain.CoffeeChatActiveStatus;
import kernel.jdon.coffeechat.dto.request.CoffeeChatCondition;
import kernel.jdon.coffeechat.dto.request.CreateCoffeeChatRequest;
import kernel.jdon.coffeechat.dto.request.UpdateCoffeeChatRequest;
import kernel.jdon.coffeechat.dto.response.ApplyCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.CreateCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.DeleteCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.FindCoffeeChatListResponse;
import kernel.jdon.coffeechat.dto.response.FindCoffeeChatResponse;
import kernel.jdon.coffeechat.dto.response.UpdateCoffeeChatResponse;
import kernel.jdon.moduleapi.domain.coffeechat.error.CoffeeChatErrorCode;
import kernel.jdon.coffeechat.repository.CoffeeChatRepository;
import kernel.jdon.coffeechatmember.domain.CoffeeChatMember;
import kernel.jdon.coffeechatmember.repsitory.CoffeeChatMemberRepository;
import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
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
	private final CoffeeChatMemberRepository coffeeChatMemberRepository;

	private CoffeeChat findExistCoffeeChat(Long coffeeChatId) {

		return coffeeChatRepository.findByIdAndIsDeletedFalse(coffeeChatId)
			.orElseThrow(() -> new ApiException(CoffeeChatErrorCode.NOT_FOUND_COFFEECHAT));
	}

	private CoffeeChat findExistAndOpenCoffeeChat(Long coffeeChatId) {

		CoffeeChat findCoffeeChat = findExistCoffeeChat(coffeeChatId);
		if (findCoffeeChat.isNotOpen()) {
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

	public CustomPageResponse findGuestCoffeeChatList(Long memberId, Pageable pageable) {
		Page<FindCoffeeChatListResponse> guestCoffeeChatPage = coffeeChatMemberRepository.findAllByMemberId(
				memberId, pageable)
			.map(CoffeeChatMember::getCoffeeChat)
			.map(FindCoffeeChatListResponse::of);

		return CustomPageResponse.of(guestCoffeeChatPage);
	}

	public CustomPageResponse findCoffeeChatList(Pageable pageable, CoffeeChatCondition coffeeChatCondition) {
		Page<FindCoffeeChatListResponse> findCoffeeChatList = coffeeChatRepository.findCoffeeChatList(pageable,
			coffeeChatCondition);
		return CustomPageResponse.of(findCoffeeChatList);
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

		validateApplyRequest(findMember, findCoffeeChat);
		findCoffeeChat.addCoffeeChatMember(findMember);

		return ApplyCoffeeChatResponse.of(findCoffeeChat.getId());
	}

	private void validateApplyRequest(Member findMember, CoffeeChat findCoffeeChat) {
		checkIfMemberIsHost(findMember, findCoffeeChat);
		checkIfAlreadyJoined(findMember, findCoffeeChat);
	}

	private void checkIfAlreadyJoined(Member findMember, CoffeeChat findCoffeeChat) {
		if (coffeeChatMemberRepository.existsByCoffeeChatIdAndMemberId(findCoffeeChat.getId(), findMember.getId())) {
			throw new ApiException(CoffeeChatErrorCode.ALREADY_JOINED_COFFEECHAT);
		}
	}

	private void checkIfMemberIsHost(Member findMember, CoffeeChat findCoffeeChat) {
		if (findMember.getId().equals(findCoffeeChat.getMember().getId())) {
			throw new ApiException(CoffeeChatErrorCode.CANNOT_JOIN_OWN_COFFEECHAT);
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
		CoffeeChat updateCoffeeChat = request.toEntity();

		validateUpdateRequest(findCoffeeChat, updateCoffeeChat);
		findCoffeeChat.updateCoffeeChat(updateCoffeeChat);

		return UpdateCoffeeChatResponse.of(findCoffeeChat.getId());
	}

	private void validateUpdateRequest(CoffeeChat findCoffeeChat, CoffeeChat updateCoffeeChat) {
		checkMeetDate(findCoffeeChat, updateCoffeeChat);
		checkTotalRecruitCount(findCoffeeChat, updateCoffeeChat);
	}

	private void checkMeetDate(CoffeeChat findCoffeeChat, CoffeeChat updateCoffeeChat) {
		if (findCoffeeChat.isExpired()) {
			throw new ApiException(CoffeeChatErrorCode.EXPIRED_COFFEECHAT);
		} else if (updateCoffeeChat.isPastDate()) {
			throw new ApiException(CoffeeChatErrorCode.MEET_DATE_ISBEFORE_NOW);
		}
	}

	private void checkTotalRecruitCount(CoffeeChat findCoffeeChat, CoffeeChat updateCoffeeChat) {
		if (findCoffeeChat.isCurrentCountGreaterThan(updateCoffeeChat.getTotalRecruitCount())) {
			throw new ApiException(CoffeeChatErrorCode.INVALID_TOTAL_RECRUIT_COUNT);
		}
	}

}
