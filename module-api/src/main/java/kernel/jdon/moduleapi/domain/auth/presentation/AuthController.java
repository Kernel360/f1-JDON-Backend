package kernel.jdon.moduleapi.domain.auth.presentation;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.moduleapi.domain.auth.application.AuthFacade;
import kernel.jdon.moduleapi.domain.auth.core.AuthCommand;
import kernel.jdon.moduleapi.domain.auth.core.AuthInfo;
import kernel.jdon.moduleapi.global.annotation.LoginUser;
import kernel.jdon.modulecommon.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {
	private final AuthFacade authFacade;
	private final AuthDtoMapper authDtoMapper;

	@PostMapping("/api/v1/register")
	public ResponseEntity<CommonResponse<AuthDto.RegisterResponse>> register(
		@RequestBody @Valid final AuthDto.RegisterRequest request) {
		final AuthCommand.RegisterRequest command = authDtoMapper.of(request);
		final AuthInfo.RegisterResponse info = authFacade.register(command);
		final AuthDto.RegisterResponse response = authDtoMapper.of(info);
		final URI uri = URI.create("/api/v1/member/" + response.getMemberId());

		return ResponseEntity.created(uri).body(CommonResponse.of(response));

	}

	@GetMapping("/api/v1/authenticate")
	public ResponseEntity<CommonResponse<AuthDto.AuthenticateResponse>> authenticate(
		@LoginUser final SessionUserInfo sessionUser) {
		boolean isLoginUser = false;
		Long memberId = null;
		if (null != sessionUser) {
			isLoginUser = true;
			memberId = sessionUser.getId();
		}
		final AuthDto.AuthenticateResponse response = AuthDto.AuthenticateResponse.of(isLoginUser, memberId);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}
}
