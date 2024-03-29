package kernel.jdon.moduleapi.domain.favorite.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kernel.jdon.moduleapi.domain.favorite.application.FavoriteFacade;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteCommand;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfo;
import kernel.jdon.moduleapi.global.annotation.LoginUser;
import kernel.jdon.moduleapi.global.dto.SessionUserInfo;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.modulecommon.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteFacade favoriteFacade;
    private final FavoriteDtoMapper favoriteDtoMapper;

    @GetMapping("/api/v1/favorites")
    public ResponseEntity<CommonResponse<FavoriteDto.FindFavoriteListResponse>> getList(
        @LoginUser final SessionUserInfo member,
        @ModelAttribute final PageInfoRequest pageInfoRequest) {
        final FavoriteInfo.FindFavoriteListResponse info = favoriteFacade.getFavoriteList(member.getId(),
            pageInfoRequest);
        final FavoriteDto.FindFavoriteListResponse response = favoriteDtoMapper.of(info);

        return ResponseEntity.ok(CommonResponse.of(response));
    }

    @PostMapping("/api/v1/favorites")
    public ResponseEntity<CommonResponse<FavoriteDto.UpdateResponse>> modify(@LoginUser final SessionUserInfo member,
        @RequestBody @Valid final FavoriteDto.UpdateRequest request) {
        final FavoriteCommand.UpdateRequest command = favoriteDtoMapper.of(request);
        final FavoriteInfo.UpdateResponse info = favoriteFacade.modify(member.getId(), command);
        final FavoriteDto.UpdateResponse response = favoriteDtoMapper.of(info);

        URI uri = URI.create("/api/v1/favorites/" + response.getLectureId());

        return ResponseEntity.created(uri).body(CommonResponse.of(response));
    }

}
