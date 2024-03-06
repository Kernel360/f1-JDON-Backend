package kernel.jdon.moduleapi.domain.coffeechat.core;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.moduleapi.domain.coffeechat.error.CoffeeChatErrorCode;
import kernel.jdon.moduleapi.domain.member.core.MemberReader;
import kernel.jdon.moduleapi.global.exception.ApiException;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.moduledomain.coffeechat.domain.CoffeeChat;
import kernel.jdon.moduledomain.coffeechatmember.domain.CoffeeChatMember;
import kernel.jdon.moduledomain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CoffeeChatServiceImpl implements CoffeeChatService {

    private final CoffeeChatReader coffeeChatReader;
    private final CoffeeChatStore coffeeChatStore;
    private final CoffeeChatInfoMapper coffeeChatInfoMapper;
    private final MemberReader memberReader;

    @Override
    @Transactional
    public CoffeeChatInfo.CreatedCoffeeChatResponse createCoffeeChat(CoffeeChatCommand.CreateCoffeeChatRequest request,
        Long memberId) {
        Member findMember = memberReader.findById(memberId);
        CoffeeChat savedCoffeeChat = coffeeChatStore.save(request.toEntity(findMember));

        return new CoffeeChatInfo.CreatedCoffeeChatResponse(savedCoffeeChat.getId());
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
    public CoffeeChatInfo.FindCoffeeChatResponse getCoffeeChat(Long coffeeChatId, Long memberId) {
        CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);
        boolean isParticipant = checkIfMemberParticipated(coffeeChatId, memberId);

        return coffeeChatInfoMapper.of(findCoffeeChat, isParticipant);
    }

    private boolean checkIfMemberParticipated(Long coffeeChatId, Long memberId) {
        boolean isParticipant;
        if (memberId != null) {
            isParticipant = coffeeChatReader.existsByCoffeeChatIdAndMemberId(coffeeChatId, memberId);
        } else {
            isParticipant = false;
        }

        return isParticipant;
    }

    @Override
    @Transactional
    public void increaseViewCount(Long coffeeChatId) {
        CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);
        findCoffeeChat.increaseViewCount();
    }

    @Override
    @Transactional
    public CoffeeChatInfo.UpdatedCoffeeChatResponse modifyCoffeeChat(CoffeeChatCommand.UpdateCoffeeChatRequest request,
        Long coffeeChatId) {
        CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);
        CoffeeChat updateCoffeeChat = request.toEntity();

        validateUpdateRequest(findCoffeeChat, updateCoffeeChat);
        coffeeChatStore.update(findCoffeeChat, updateCoffeeChat);

        return new CoffeeChatInfo.UpdatedCoffeeChatResponse(findCoffeeChat.getId());
    }

    private void validateUpdateRequest(CoffeeChat findCoffeeChat, CoffeeChat updateCoffeeChat) {
        checkMeetDate(findCoffeeChat, updateCoffeeChat);
        checkTotalRecruitCount(findCoffeeChat, updateCoffeeChat);
    }

    private void checkMeetDate(CoffeeChat findCoffeeChat, CoffeeChat updateCoffeeChat) {
        if (findCoffeeChat.isExpired()) {
            throw CoffeeChatErrorCode.EXPIRED_COFFEECHAT.throwException();
        }
        if (updateCoffeeChat.isExpired()) {
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
    public CoffeeChatInfo.DeletedCoffeeChatResponse deleteCoffeeChat(Long coffeeChatId) {
        CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);
        coffeeChatStore.deleteById(findCoffeeChat.getId());

        return new CoffeeChatInfo.DeletedCoffeeChatResponse(findCoffeeChat.getId());
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
    public CoffeeChatInfo.AppliedCoffeeChatResponse applyCoffeeChat(Long coffeeChatId, Long memberId) {
        CoffeeChat findCoffeeChat = findExistAndOpenCoffeeChat(coffeeChatId);
        Member findMember = memberReader.findById(memberId);

        validateApplyRequest(findMember, findCoffeeChat);
        findCoffeeChat.addCoffeeChatMember(findMember);

        return new CoffeeChatInfo.AppliedCoffeeChatResponse(findCoffeeChat.getId());
    }

    private void validateApplyRequest(Member findMember, CoffeeChat findCoffeeChat) {
        checkIfMemberIsHost(findMember, findCoffeeChat);
        checkIfAlreadyJoined(findMember, findCoffeeChat);
    }

    private void checkIfAlreadyJoined(Member findMember, CoffeeChat findCoffeeChat) {
        if (coffeeChatReader.existsByCoffeeChatIdAndMemberId(findCoffeeChat.getId(), findMember.getId())) {
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

    @Override
    @Transactional
    public CoffeeChatInfo.CanceledCoffeeChatResponse cancelCoffeeChat(Long coffeeChatId, Long memberId) {
        CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);

        CoffeeChatMember coffeeChatMember = coffeeChatReader.findCoffeeChatMember(coffeeChatId, memberId);
        findCoffeeChat.removeCoffeeChatMember(coffeeChatMember);

        return new CoffeeChatInfo.CanceledCoffeeChatResponse(findCoffeeChat.getId());
    }

}


