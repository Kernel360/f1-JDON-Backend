package kernel.jdon.favorite.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.dto.response.CommonResponse;
import kernel.jdon.favorite.dto.request.UpdateFavoriteRequest;
import kernel.jdon.favorite.dto.response.FindFavoriteResponse;
import kernel.jdon.favorite.dto.response.UpdateFavoriteResponse;
import kernel.jdon.favorite.service.FavoriteService;
import kernel.jdon.global.annotation.LoginUser;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FavoriteController {

	private final FavoriteService favoriteService;

	@GetMapping("/api/v1/favorites")
	public ResponseEntity<CommonResponse> getAll() {
		List<FindFavoriteResponse> findFavoriteResponseList = new ArrayList<>();
		for (long i = 1; i < 10; i++) {
			FindFavoriteResponse findFavoriteLectureResponse = FindFavoriteResponse.builder()
				.lectureId(i)
				.title("스프링부트 초급편_" + i)
				.lectureUrl("www.inflearn.com/we234")
				.imageUrl(
					"https://cdn.inflearn.com/public/courses/330459/cover/00d1bd8e-3b9d-4c62-b801-fea717c942fa/330459-eng.png")
				.instructor("김영한")
				.studentCount(5332)
				.price(180000)
				.isFavorite(true)
				.build();

			findFavoriteResponseList.add(findFavoriteLectureResponse);
		}

		return ResponseEntity.ok(CommonResponse.of(findFavoriteResponseList));
	}

	@PostMapping("/api/v1/favorites")
	public ResponseEntity<CommonResponse> update(@LoginUser SessionUserInfo user,
		@RequestBody UpdateFavoriteRequest updateFavoriteRequest) {
		UpdateFavoriteResponse updateFavoriteResponse = favoriteService.update(user.getId(),
			updateFavoriteRequest);
		URI uri = URI.create("/api/v1/favorites/" + updateFavoriteResponse.getLectureId());

		return ResponseEntity.created(uri).body(CommonResponse.of(updateFavoriteResponse));
	}
}
