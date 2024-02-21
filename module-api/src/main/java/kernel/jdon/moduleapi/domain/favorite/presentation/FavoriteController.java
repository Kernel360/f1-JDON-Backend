package kernel.jdon.moduleapi.domain.favorite.presentation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.moduleapi.domain.favorite.application.FavoriteFacade;
import kernel.jdon.moduleapi.domain.favorite.core.FavoriteInfo;
import kernel.jdon.moduleapi.global.annotation.LoginUser;
import kernel.jdon.modulecommon.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FavoriteController {
	private final FavoriteFacade favoriteFacade;
	private final FavoriteDtoMapper favoriteDtoMapper;

	@GetMapping("/api/v1/favorites")
	public ResponseEntity<CommonResponse> getList(@LoginUser SessionUserInfo user,
		@PageableDefault(size = 12) Pageable pageable) {
		final FavoriteInfo.FindPageResponse info = favoriteFacade.getList(user.getId(), pageable);
		final FavoriteDto.FindPageResponse response = favoriteDtoMapper.of(info);

		return ResponseEntity.ok(CommonResponse.of(response));
	}

}
