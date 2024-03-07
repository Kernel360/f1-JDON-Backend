package kernel.jdon.moduleapi.domain.coffeechat.presentation;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kernel.jdon.moduleapi.domain.coffeechat.application.CoffeeChatFacade;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatCommand;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatSortCondition;
import kernel.jdon.moduleapi.global.annotation.LoginUser;
import kernel.jdon.moduleapi.global.dto.SessionUserInfo;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.modulecommon.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CoffeeChatController {

    private final CoffeeChatFacade coffeeChatFacade;
    private final CoffeeChatDtoMapper coffeeChatDtoMapper;

    @GetMapping("/api/v1/coffeechats")
    public ResponseEntity<CommonResponse<CoffeeChatInfo.FindCoffeeChatListResponse>> getCoffeeChatList(
        @ModelAttribute final PageInfoRequest pageInfoRequest,
        @RequestParam(value = "sort", defaultValue = "") final CoffeeChatSortCondition sort,
        @RequestParam(value = "keyword", defaultValue = "") final String keyword,
        @RequestParam(value = "jobCategory", defaultValue = "") final Long jobCategory) {

        CoffeeChatCommand.FindCoffeeChatListRequest request = coffeeChatDtoMapper.of(
            new CoffeeChatCondition(sort, keyword, jobCategory));
        CoffeeChatInfo.FindCoffeeChatListResponse info = coffeeChatFacade.getCoffeeChatList(
            pageInfoRequest, request);
        CoffeeChatDto.FindCoffeeChatListResponse response = coffeeChatDtoMapper.of(info);

        return ResponseEntity.ok(CommonResponse.of(response));
    }

    @GetMapping("/api/v1/coffeechats/{id}")
    public ResponseEntity<CommonResponse<CoffeeChatDto.FindCoffeeChatResponse>> getCoffeeChat(
        @PathVariable(name = "id") Long coffeeChatId,
        @LoginUser SessionUserInfo member
    ) {
        Long memberId = getMemberId(member);
        CoffeeChatInfo.FindCoffeeChatResponse info = coffeeChatFacade.getCoffeeChat(coffeeChatId, memberId);
        CoffeeChatDto.FindCoffeeChatResponse response = coffeeChatDtoMapper.of(info);

        return ResponseEntity.ok(CommonResponse.of(response));
    }

    private Long getMemberId(SessionUserInfo member) {
        return Optional.ofNullable(member)
            .map(SessionUserInfo::getId)
            .orElse(null);
    }

    @PostMapping("/api/v1/coffeechats/{id}")
    public ResponseEntity<CommonResponse<CoffeeChatDto.ApplyCoffeeChatResponse>> applyCoffeeChat(
        @PathVariable(name = "id") Long coffeeChatId,
        @LoginUser SessionUserInfo member
    ) {
        CoffeeChatInfo.ApplyCoffeeChatResponse info = coffeeChatFacade.applyCoffeeChat(
            coffeeChatId, member.getId());
        CoffeeChatDto.ApplyCoffeeChatResponse response = coffeeChatDtoMapper.of(info);

        return ResponseEntity.ok().body(CommonResponse.of(response));
    }

    @PostMapping("/api/v1/coffeechats/{id}/cancel")
    public ResponseEntity<CommonResponse<CoffeeChatDto.CancelCoffeeChatResponse>> cancelCoffeeChat(
        @PathVariable(name = "id") Long coffeeChatId,
        @LoginUser SessionUserInfo member
    ) {
        CoffeeChatInfo.CancelCoffeeChatResponse info = coffeeChatFacade.cancelCoffeeChatApplication(
            coffeeChatId, member.getId());
        CoffeeChatDto.CancelCoffeeChatResponse response = coffeeChatDtoMapper.of(info);

        return ResponseEntity.ok(CommonResponse.of(response));
    }

    @PostMapping("/api/v1/coffeechats")
    public ResponseEntity<CommonResponse<CoffeeChatDto.CreateCoffeeChatResponse>> createCoffeeChat(
        @RequestBody @Valid CoffeeChatDto.CreateCoffeeChatRequest request,
        @LoginUser SessionUserInfo member
    ) {
        CoffeeChatCommand.CreateCoffeeChatRequest createCommand = coffeeChatDtoMapper.of(request);
        CoffeeChatInfo.CreateCoffeeChatResponse info = coffeeChatFacade.createCoffeeChat(createCommand,
            member.getId());
        CoffeeChatDto.CreateCoffeeChatResponse response = coffeeChatDtoMapper.of(info);
        URI uri = URI.create("/v1/coffeechats/" + info.getCoffeeChatId());

        return ResponseEntity.created(uri).body(CommonResponse.of(response));
    }

    @PutMapping("/api/v1/coffeechats/{id}")
    public ResponseEntity<CommonResponse<CoffeeChatDto.UpdateCoffeeChatResponse>> modifyCoffeeChat(
        @RequestBody @Valid CoffeeChatDto.UpdateCoffeeChatRequest request,
        @PathVariable(name = "id") Long coffeeChatId,
        @LoginUser SessionUserInfo member
    ) {
        CoffeeChatCommand.UpdateCoffeeChatRequest updateCommand = coffeeChatDtoMapper.of(request, coffeeChatId,
            member.getId());
        CoffeeChatInfo.UpdateCoffeeChatResponse info = coffeeChatFacade.modifyCoffeeChat(updateCommand);
        CoffeeChatDto.UpdateCoffeeChatResponse response = coffeeChatDtoMapper.of(info);

        return ResponseEntity.ok().body(CommonResponse.of(response));
    }

    @DeleteMapping("/api/v1/coffeechats/{id}")
    public ResponseEntity<CommonResponse<CoffeeChatDto.DeleteCoffeeChatResponse>> removeCoffeeChat(
        @PathVariable(name = "id") Long coffeeChatId,
        @LoginUser SessionUserInfo member
    ) {
        CoffeeChatInfo.DeleteCoffeeChatResponse info = coffeeChatFacade.deleteCoffeeChat(
            coffeeChatId, member.getId());
        CoffeeChatDto.DeleteCoffeeChatResponse response = coffeeChatDtoMapper.of(info);

        return ResponseEntity.ok().body(CommonResponse.of(response));
    }

    @GetMapping("/api/v1/coffeechats/guest")
    public ResponseEntity<CommonResponse<CoffeeChatInfo.FindCoffeeChatListResponse>> getGuestCoffeeChatList(
        @ModelAttribute PageInfoRequest pageInfoRequest,
        @LoginUser SessionUserInfo member
    ) {
        CoffeeChatInfo.FindCoffeeChatListResponse info = coffeeChatFacade.getGuestCoffeeChatList(
            member.getId(), pageInfoRequest);
        CoffeeChatDto.FindCoffeeChatListResponse response = coffeeChatDtoMapper.of(info);

        return ResponseEntity.ok().body(CommonResponse.of(response));
    }

    @GetMapping("/api/v1/coffeechats/host")
    public ResponseEntity<CommonResponse<CoffeeChatInfo.FindCoffeeChatListResponse>> getHostCoffeeChatList(
        @ModelAttribute PageInfoRequest pageInfoRequest,
        @LoginUser SessionUserInfo member
    ) {
        CoffeeChatInfo.FindCoffeeChatListResponse info = coffeeChatFacade.getHostCoffeeChatList(
            member.getId(), pageInfoRequest);
        CoffeeChatDto.FindCoffeeChatListResponse response = coffeeChatDtoMapper.of(info);

        return ResponseEntity.ok().body(CommonResponse.of(response));
    }
}
