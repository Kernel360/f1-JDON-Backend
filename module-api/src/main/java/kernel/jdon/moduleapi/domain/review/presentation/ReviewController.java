package kernel.jdon.moduleapi.domain.review.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.moduleapi.domain.review.application.ReviewFacade;
import kernel.jdon.moduleapi.domain.review.core.ReviewCommand;
import kernel.jdon.moduleapi.domain.review.core.ReviewInfo;
import kernel.jdon.moduleapi.global.annotation.LoginUser;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.modulecommon.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ReviewController {
	private final ReviewFacade reviewFacade;
	private final ReviewDtoMapper reviewDtoMapper;

	@PostMapping("/api/v1/reviews")
	public ResponseEntity<CommonResponse<ReviewDto.CreateReviewResponse>> createReview(
		@RequestBody @Valid final ReviewDto.CreateReviewRequest request,
		@LoginUser final SessionUserInfo sessionUserInfo) {
		final ReviewCommand.CreateReviewRequest command = reviewDtoMapper.of(request, sessionUserInfo.getId());
		final ReviewInfo.CreateReviewResponse info = reviewFacade.createReview(command);
		final ReviewDto.CreateReviewResponse response = reviewDtoMapper.of(info);
		final URI uri = URI.create("/api/v1/reviews" + response.getCommandId());

		return ResponseEntity.created(uri).body(CommonResponse.of(response));
	}

	@GetMapping("/api/v1/reviews/{jdId}")
	public ResponseEntity<CommonResponse<ReviewDto.CreateReviewResponse>> findReviewList(
		@PathVariable(name = "jdId") final Long jdId,
		@RequestParam(value = "page", defaultValue = "0") final int page,
		@RequestParam(value = "size", defaultValue = "6") final int size) {
		final ReviewInfo.FindReviewListResponse info = reviewFacade.findReviewList(jdId,
			new PageInfoRequest(page, size));
		final ReviewDto.FindReviewListResponse response = reviewDtoMapper.of(info);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

}
