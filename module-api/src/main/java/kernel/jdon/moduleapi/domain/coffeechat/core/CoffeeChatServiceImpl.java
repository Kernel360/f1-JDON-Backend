package kernel.jdon.moduleapi.domain.coffeechat.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.coffeechat.domain.CoffeeChat;
import kernel.jdon.coffeechatmember.domain.CoffeeChatMember;
import kernel.jdon.member.domain.Member;
import kernel.jdon.moduleapi.domain.coffeechat.error.CoffeeChatErrorCode;
import kernel.jdon.moduleapi.domain.coffeechat.infrastructure.CoffeeChatMemberRepository;
import kernel.jdon.moduleapi.domain.member.error.MemberErrorCode;
import kernel.jdon.moduleapi.domain.member.infrastructure.MemberRepository;
import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
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
    //TODO: MemberReader 추상화에 의존하도록 변경?
    private final CoffeeChatMemberRepository coffeeChatMemberRepository;
    //TODO: MemberReader 추상화에 의존하도록 변경
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Long createCoffeeChat(CoffeeChatCommand.CreateCoffeeChatRequest request, Long memberId) {
        //TODO: MemberReader 추상화에 의존하도록 변경
        Member findMember = memberRepository.findById(memberId)
            .orElseThrow(MemberErrorCode.NOT_FOUND_MEMBER::throwException);
        CoffeeChat savedCoffeeChat = coffeeChatStore.save(request.toEntity(findMember));

        return savedCoffeeChat.getId();

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
    public CoffeeChatInfo.FindCoffeeChatResponse getCoffeeChat(Long coffeeChatId) {
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
    public Long updateCoffeeChat(CoffeeChatCommand.UpdateCoffeeChatRequest request, Long coffeeChatId) {
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
            throw CoffeeChatErrorCode.EXPIRED_COFFEECHAT.throwException();
        } else if (updateCoffeeChat.isPastDate()) {
            throw CoffeeChatErrorCode.MEET_DATE_ISBEFORE_NOW.throwException();
        }
    }

    private void checkTotalRecruitCount(CoffeeChat findCoffeeChat, CoffeeChat updateCoffeeChat) {
        if (findCoffeeChat.isCurrentCountGreaterThan(updateCoffeeChat.getTotalRecruitCount())) {
            throw CoffeeChatErrorCode.INVALID_TOTAL_RECRUIT_COUNT.throwException();
        }
    }

    @Override
    @Transactional
    public Long deleteCoffeeChat(Long coffeeChatId) {
        CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);
        coffeeChatStore.delete(findCoffeeChat.getId());

        return findCoffeeChat.getId();
    }

    @Override
    public CustomPageResponse<CoffeeChatInfo.FindCoffeeChat> getGuestCoffeeChatList(Long memberId,
        Pageable pageable) {
        Page<CoffeeChatInfo.FindCoffeeChat> guestCoffeeChatPage = coffeeChatReader.findCoffeeChatMemberListByMemberId(
                memberId,
                pageable)
            .map(CoffeeChatMember::getCoffeeChat)
            .map(coffeeChatInfoMapper::listOf);

        return CustomPageResponse.of(guestCoffeeChatPage);
    }

    @Override
    public CustomPageResponse<CoffeeChatInfo.FindCoffeeChat> getHostCoffeeChatList(Long memberId,
        Pageable pageable) {
        Page<CoffeeChatInfo.FindCoffeeChat> hostCoffeeChatPage = coffeeChatReader.findCoffeeChatListByMemberId(memberId,
                pageable)
            .map(coffeeChatInfoMapper::listOf);

        return CustomPageResponse.of(hostCoffeeChatPage);
    }

    @Override
    @Transactional
    public Long applyCoffeeChat(Long coffeeChatId, Long memberId) {
        CoffeeChat findCoffeeChat = findExistAndOpenCoffeeChat(coffeeChatId);
        Member findMember = memberRepository.findById(memberId)
            .orElseThrow(MemberErrorCode.NOT_FOUND_MEMBER::throwException);

        validateApplyRequest(findMember, findCoffeeChat);
        findCoffeeChat.addCoffeeChatMember(findMember);

        return findCoffeeChat.getId();
    }

    private void validateApplyRequest(Member findMember, CoffeeChat findCoffeeChat) {
        checkIfMemberIsHost(findMember, findCoffeeChat);
        checkIfAlreadyJoined(findMember, findCoffeeChat);
    }

    private void checkIfAlreadyJoined(Member findMember, CoffeeChat findCoffeeChat) {
        //TODO: MemberReader? 추상화에 의존하도록 변경
        if (coffeeChatMemberRepository.existsByCoffeeChatIdAndMemberId(findCoffeeChat.getId(), findMember.getId())) {
            throw new ApiException(CoffeeChatErrorCode.ALREADY_JOINED_COFFEECHAT);
        }
    }

    private void checkIfMemberIsHost(Member findMember, CoffeeChat findCoffeeChat) {
        if (findMember.getId().equals(findCoffeeChat.getMember().getId())) {
            throw new ApiException(CoffeeChatErrorCode.CANNOT_JOIN_OWN_COFFEECHAT);
        }
    }

    private CoffeeChat findExistAndOpenCoffeeChat(Long coffeeChatId) {
        CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);
        if (findCoffeeChat.isNotOpen()) {
            throw new ApiException(CoffeeChatErrorCode.NOT_OPEN_COFFEECHAT);
        }

        return findCoffeeChat;
    }
}


