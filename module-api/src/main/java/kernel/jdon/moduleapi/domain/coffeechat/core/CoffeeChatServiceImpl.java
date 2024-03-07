package kernel.jdon.moduleapi.domain.coffeechat.core;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kernel.jdon.moduleapi.domain.coffeechat.error.CoffeeChatErrorCode;
import kernel.jdon.moduleapi.domain.member.core.MemberReader;
import kernel.jdon.moduleapi.global.exception.ApiException;
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
    public CoffeeChatInfo.CreateCoffeeChatResponse createCoffeeChat(
        CoffeeChatCommand.CreateCoffeeChatRequest request,
        Long memberId
    ) {
        Member findMember = memberReader.findById(memberId);
        CoffeeChat savedCoffeeChat = coffeeChatStore.save(request.toEntity(findMember));

        return new CoffeeChatInfo.CreateCoffeeChatResponse(savedCoffeeChat.getId());
    }

    @Override
    public CoffeeChatInfo.FindCoffeeChatListResponse getCoffeeChatList(
        final PageInfoRequest pageInfoRequest,
        final CoffeeChatCommand.FindCoffeeChatListRequest command
    ) {
        return coffeeChatReader.findCoffeeChatList(pageInfoRequest, command);
    }

    @Override
    public CoffeeChatInfo.FindCoffeeChatResponse getCoffeeChat(Long coffeeChatId, Long memberId) {
        CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);
        boolean isParticipant = checkIfMemberParticipated(coffeeChatId, memberId);

        return coffeeChatInfoMapper.of(findCoffeeChat, isParticipant);
    }

    private boolean checkIfMemberParticipated(Long coffeeChatId, Long memberId) {
        return Optional.ofNullable(memberId)
            .map(id -> coffeeChatReader.existsByCoffeeChatIdAndMemberId(coffeeChatId, id))
            .orElse(false);
    }

    @Override
    @Transactional
    public void increaseViewCount(Long coffeeChatId) {
        CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);
        findCoffeeChat.increaseViewCount();
    }

    @Override
    @Transactional
    public CoffeeChatInfo.UpdateCoffeeChatResponse modifyCoffeeChat(CoffeeChatCommand.UpdateCoffeeChatRequest request) {
        CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(request.getCoffeeChatId());
        CoffeeChat updateCoffeeChat = request.toEntity();

        validateUpdateRequest(findCoffeeChat, updateCoffeeChat, request.getMemberId());
        coffeeChatStore.update(findCoffeeChat, updateCoffeeChat);

        return new CoffeeChatInfo.UpdateCoffeeChatResponse(findCoffeeChat.getId());
    }

    private void validateUpdateRequest(CoffeeChat findCoffeeChat, CoffeeChat updateCoffeeChat, Long memberId) {
        if (!isMemberHost(memberId, findCoffeeChat)) {
            throw new ApiException(CoffeeChatErrorCode.UNAUTHORIZED_COFFEECHAT_UPDATE);
        }
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
    public CoffeeChatInfo.DeleteCoffeeChatResponse deleteCoffeeChat(Long coffeeChatId, Long memberId) {
        CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);
        validateDeleteRequest(memberId, findCoffeeChat);
        coffeeChatStore.deleteById(findCoffeeChat.getId());

        return new CoffeeChatInfo.DeleteCoffeeChatResponse(findCoffeeChat.getId());
    }

    private void validateDeleteRequest(Long memberId, CoffeeChat findCoffeeChat) {
        if (!isMemberHost(memberId, findCoffeeChat)) {
            throw new ApiException(CoffeeChatErrorCode.UNAUTHORIZED_COFFEECHAT_DELETE);
        }
    }

    @Override
    public CoffeeChatInfo.FindCoffeeChatListResponse getGuestCoffeeChatList(
        Long memberId,
        PageInfoRequest pageInfoRequest
    ) {
        return coffeeChatReader.findGuestCoffeeChatList(memberId, pageInfoRequest);
    }

    @Override
    public CoffeeChatInfo.FindCoffeeChatListResponse getHostCoffeeChatList(
        Long memberId,
        PageInfoRequest pageInfoRequest
    ) {
        return coffeeChatReader.findHostCoffeeChatList(memberId, pageInfoRequest);
    }

    @Override
    @Transactional
    public CoffeeChatInfo.ApplyCoffeeChatResponse applyCoffeeChat(Long coffeeChatId, Long memberId) {
        CoffeeChat findCoffeeChat = findExistAndOpenCoffeeChat(coffeeChatId);
        Member findMember = memberReader.findById(memberId);

        validateApplyRequest(findMember, findCoffeeChat);
        findCoffeeChat.addCoffeeChatMember(findMember);

        return new CoffeeChatInfo.ApplyCoffeeChatResponse(findCoffeeChat.getId());
    }

    private void validateApplyRequest(Member findMember, CoffeeChat findCoffeeChat) {
        if (isMemberHost(findMember.getId(), findCoffeeChat)) {
            throw new ApiException(CoffeeChatErrorCode.CANNOT_JOIN_OWN_COFFEECHAT);
        }
        checkIfAlreadyJoined(findMember, findCoffeeChat);
    }

    private void checkIfAlreadyJoined(Member findMember, CoffeeChat findCoffeeChat) {
        if (coffeeChatReader.existsByCoffeeChatIdAndMemberId(findCoffeeChat.getId(), findMember.getId())) {
            throw new ApiException(CoffeeChatErrorCode.ALREADY_JOINED_COFFEECHAT);
        }
    }

    private boolean isMemberHost(Long memberId, CoffeeChat findCoffeeChat) {
        return memberId.equals(findCoffeeChat.getMember().getId());
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
    public CoffeeChatInfo.CancelCoffeeChatResponse cancelCoffeeChat(Long coffeeChatId, Long memberId) {
        CoffeeChat findCoffeeChat = coffeeChatReader.findExistCoffeeChat(coffeeChatId);

        CoffeeChatMember findCoffeeChatMember = coffeeChatReader.findCoffeeChatMember(coffeeChatId, memberId);
        coffeeChatStore.cancel(findCoffeeChat, findCoffeeChatMember);

        return new CoffeeChatInfo.CancelCoffeeChatResponse(findCoffeeChat.getId());
    }

}


