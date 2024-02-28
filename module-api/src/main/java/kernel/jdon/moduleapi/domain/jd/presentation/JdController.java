package kernel.jdon.moduleapi.domain.jd.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.moduleapi.domain.jd.application.JdFacade;
import kernel.jdon.moduleapi.domain.jd.core.JdInfo;
import kernel.jdon.moduleapi.global.page.PageInfoRequest;
import kernel.jdon.modulecommon.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JdController {
	private final JdFacade jdFacade;
	private final JdDtoMapper jdDtoMapper;

	@GetMapping("/api/v1/jds/{id}")
	public ResponseEntity<CommonResponse<JdDto.FindWantedJdResponse>> getJd(
		@PathVariable(name = "id") final Long jdId) {
		final JdInfo.FindWantedJdResponse info = jdFacade.getJd(jdId);
		final JdDto.FindWantedJdResponse response = jdDtoMapper.of(info);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

	@GetMapping("/api/v1/jds")
	public ResponseEntity<CommonResponse<JdDto.FindWantedJdListResponse>> getJdList(
		@RequestParam(value = "page", defaultValue = "0") final int page,
		@RequestParam(value = "size", defaultValue = "12") final int size,
		@RequestParam(value = "keyword", defaultValue = "") final String keyword) {
		final JdInfo.FindWantedJdListResponse info = jdFacade.getJdList(new PageInfoRequest(page, size), keyword);
		final JdDto.FindWantedJdListResponse response = jdDtoMapper.of(info);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}
}
