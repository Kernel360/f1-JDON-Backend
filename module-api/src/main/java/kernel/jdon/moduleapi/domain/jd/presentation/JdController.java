package kernel.jdon.moduleapi.domain.jd.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.moduleapi.domain.jd.application.JdFacade;
import kernel.jdon.moduleapi.domain.jd.core.JdInfo;
import kernel.jdon.modulecommon.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JdController {
	private final JdFacade jdFacade;
	private final JdDtoMapper jdDtoMapper;

	@GetMapping("/api/v1/jds/{id}")
	public ResponseEntity<CommonResponse<JdDto.FindWantedJdResponse>> getJd(
		@PathVariable(name = "id") Long jdId) {
		JdInfo.FindWantedJdResponse info = jdFacade.getJd(jdId);
		JdDto.FindWantedJdResponse response = jdDtoMapper.of(info);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

}
