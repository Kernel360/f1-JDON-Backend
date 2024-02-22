package kernel.jdon.moduleapi.domain.coffeechat.core;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.coffeechat.error.CoffeeChatErrorCode;
import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import kernel.jdon.moduleapi.domain.member.infrastructure.MemberRepository;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CoffeeChatServiceImpl implements CoffeeChatService {

    private final CoffeeChatReader coffeeChatReader;
    private final CoffeeChatInfoMapper coffeeChatInfoMapper;
    private final CoffeeChatStore coffeeChatStore;
    //TODO: MemberReader 추상화에 의존하도록 변경
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long createCoffeeChat(CoffeeChatCommand.CreateRequest request, Long memberId) {
        //TODO: MemberReader 추상화에 의존하도록 변경
        Member findMember = memberRepository.findById(memberId)
            .orElseThrow(MemberErrorCode.NOT_FOUND_MEMBER::throwException);
        CoffeeChat storedCoffeeChat = coffeeChatStore.store(request.toEntity(findMember));

        return storedCoffeeChat.getId();

    }

	@Override
	public CoffeeChatInfo.FindCoffeeChatListResponse getCoffeeChatList(
		final PageInfoRequest pageInfoRequest,
		final CoffeeChatCommand.FindCoffeeChatListRequest command) {
		final CoffeeChatInfo.FindCoffeeChatListResponse coffeeChatList = coffeeChatReader.findCoffeeChatList(
			pageInfoRequest, command);

		return coffeeChatList;
	}

	@Override
	public CoffeeChatInfo.FindResponse getCoffeeChat(Long coffeeChatId) {
		CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);

        return coffeeChatInfoMapper.of(findCoffeeChat);
    }

    @Override
    @Transactional
    public void increaseViewCount(Long coffeeChatId) {
        CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);
        findCoffeeChat.increaseViewCount();
    }

    @Override
    @Transactional
    public Long updateCoffeeChat(CoffeeChatCommand.UpdateRequest request, Long coffeeChatId) {
        CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);
        CoffeeChat updateCoffeeChat = request.toEntity();

        validateUpdateRequest(findCoffeeChat, updateCoffeeChat);
        findCoffeeChat.updateCoffeeChat(updateCoffeeChat);

        return findCoffeeChat.getId();
    }

    private void validateUpdateRequest(CoffeeChat findCoffeeChat, CoffeeChat updateCoffeeChat) {
        checkMeetDate(findCoffeeChat, updateCoffeeChat);
        checkTotalRecruitCount(findCoffeeChat, updateCoffeeChat);
    }

    private void checkMeetDate(CoffeeChat findCoffeeChat, CoffeeChat updateCoffeeChat) {
        if (findCoffeeChat.isExpired()) {
            CoffeeChatErrorCode.EXPIRED_COFFEECHAT.throwException();
        } else if (updateCoffeeChat.isPastDate()) {
            CoffeeChatErrorCode.MEET_DATE_ISBEFORE_NOW.throwException();
        }
    }

    private void checkTotalRecruitCount(CoffeeChat findCoffeeChat, CoffeeChat updateCoffeeChat) {
        if (findCoffeeChat.isCurrentCountGreaterThan(updateCoffeeChat.getTotalRecruitCount())) {
            CoffeeChatErrorCode.INVALID_TOTAL_RECRUIT_COUNT.throwException();
        }
    }

    @Override
    @Transactional
    public Long deleteCoffeeChat(Long coffeeChatId) {
        CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);
        coffeeChatStore.delete(findCoffeeChat.getId());

        return findCoffeeChat.getId();
    }
}


