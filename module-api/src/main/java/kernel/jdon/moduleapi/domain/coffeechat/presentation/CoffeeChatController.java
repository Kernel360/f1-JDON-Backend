package kernel.jdon.moduleapi.domain.coffeechat.presentation;

import java.net.URI;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.moduleapi.domain.coffeechat.application.CoffeeChatFacade;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatCommand;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatInfo;
import kernel.jdon.moduleapi.domain.coffeechat.core.CoffeeChatSortCondition;
import kernel.jdon.moduleapi.global.annotation.LoginUser;
import kernel.jdon.moduleapi.global.page.CustomPageResponse;
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
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "12") int size,
        @RequestParam(value = "sort", defaultValue = "") CoffeeChatSortCondition sort,
        @RequestParam(value = "keyword", defaultValue = "") String keyword,
        @RequestParam(value = "jobCategory", defaultValue = "") Long jobCategory) {

        CoffeeChatCommand.FindCoffeeChatListRequest request = coffeeChatDtoMapper.of(
            new CoffeeChatCondition(sort, keyword, jobCategory));
        CoffeeChatInfo.FindCoffeeChatListResponse info = coffeeChatFacade.getCoffeeChatList(
            new PageInfoRequest(page, size), request);
        CoffeeChatDto.FindCoffeeChatListResponse response = coffeeChatDtoMapper.of(info);

        return ResponseEntity.ok(CommonResponse.of(response));
    }

    @GetMapping("/api/v1/coffeechats/{id}")
    public ResponseEntity<CommonResponse<CoffeeChatDto.FindCoffeeChatResponse>> getCoffeeChat(
        @PathVariable(name = "id") Long coffeeChatId) {
        CoffeeChatInfo.FindCoffeeChatResponse info = coffeeChatFacade.getCoffeeChat(coffeeChatId);
        CoffeeChatDto.FindCoffeeChatResponse response = coffeeChatDtoMapper.of(info);

        return ResponseEntity.ok(CommonResponse.of(response));
    }

    @PostMapping("/api/v1/coffeechats/{id}")
    public ResponseEntity<CommonResponse<Long>> applyCoffeeChat(
        @PathVariable(name = "id") Long coffeeChatId,
        @LoginUser SessionUserInfo member
    ) {
        Long appliedCoffeeChatId = coffeeChatFacade.applyCoffeeChat(coffeeChatId, member.getId());

        return ResponseEntity.ok().body(CommonResponse.of(appliedCoffeeChatId));
    }

    @PostMapping("/api/v1/coffeechats")
    public ResponseEntity<CommonResponse<Long>> saveCoffeeChat(
        @RequestBody CoffeeChatDto.CreateCoffeeChatRequest request,
        @LoginUser SessionUserInfo member
    ) {
        CoffeeChatCommand.CreateCoffeeChatRequest createCommand = coffeeChatDtoMapper.of(request);
        Long savedCoffeeChatId = coffeeChatFacade.saveCoffeeChat(createCommand, member.getId());
        URI uri = URI.create("/v1/coffeechats/" + savedCoffeeChatId);

        return ResponseEntity.created(uri).body(CommonResponse.of(savedCoffeeChatId));
    }

    @PutMapping("/api/v1/coffeechats/{id}")
    public ResponseEntity<CommonResponse<Long>> modifyCoffeeChat(
        @PathVariable(name = "id") Long coffeeChatId,
        @RequestBody CoffeeChatDto.UpdateCoffeeChatRequest request
    ) {
        CoffeeChatCommand.UpdateCoffeeChatRequest updateCommand = coffeeChatDtoMapper.of(request);
        Long updatedCoffeeChatId = coffeeChatFacade.updateCoffeeChat(updateCommand, coffeeChatId);

        return ResponseEntity.ok().body(CommonResponse.of(updatedCoffeeChatId));
    }

    @DeleteMapping("/api/v1/coffeechats/{id}")
    public ResponseEntity<CommonResponse<Long>> removeCoffeeChat(@PathVariable(name = "id") Long coffeeChatId) {
        Long deletedCoffeeChatId = coffeeChatFacade.deleteCoffeeChat(coffeeChatId);

        return ResponseEntity.ok().body(CommonResponse.of(deletedCoffeeChatId));
    }

    @GetMapping("/api/v1/coffeechats/guest")
    public ResponseEntity<CommonResponse<CustomPageResponse<CoffeeChatInfo.FindCoffeeChat>>> getGuestCoffeeChatList(
        @LoginUser SessionUserInfo member,
        @PageableDefault(size = 12) Pageable pageable
    ) {
        CustomPageResponse<CoffeeChatInfo.FindCoffeeChat> response = coffeeChatFacade.getGuestCoffeeChatList(
            member.getId(), pageable);

        return ResponseEntity.ok().body(CommonResponse.of(response));
    }

    @GetMapping("/api/v1/coffeechats/host")
    public ResponseEntity<CommonResponse<CustomPageResponse<CoffeeChatInfo.FindCoffeeChat>>> getHostCoffeeChatList(
        @LoginUser SessionUserInfo member,
        @PageableDefault(size = 12) Pageable pageable
    ) {
        CustomPageResponse<CoffeeChatInfo.FindCoffeeChat> response = coffeeChatFacade.getHostCoffeeChatList(
            member.getId(), pageable);

        return ResponseEntity.ok().body(CommonResponse.of(response));
    }
}
