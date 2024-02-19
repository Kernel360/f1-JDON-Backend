package kernel.jdon.auth.controller;

import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.auth.dto.request.RegisterRequest;
import kernel.jdon.auth.dto.response.RegisterResponse;
import kernel.jdon.auth.dto.response.WithdrawResponse;
import kernel.jdon.auth.service.AuthService;
import kernel.jdon.dto.response.CommonResponse;
import kernel.jdon.moduleapi.global.annotation.LoginUser;
import kernel.jdon.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

	private final MemberService memberService;
	private final AuthService authService;

	@PostMapping("/api/v1/register")
	public ResponseEntity<CommonResponse> register(@RequestBody RegisterRequest registerRequest) {

		log.info("registerRequest: {}", registerRequest);
		Long registerMemberId = memberService.register(registerRequest);
		URI uri = URI.create("/api/v1/member/" + registerMemberId);

		return ResponseEntity.created(uri).body(CommonResponse.of(RegisterResponse.of(registerMemberId)));
	}

	@DeleteMapping("/api/v1/withdraw")
	public ResponseEntity<CommonResponse> withdraw(@LoginUser SessionUserInfo sessionUser) {
		Long withdrawMemberId = authService.withdraw(sessionUser);

		return ResponseEntity.ok(CommonResponse.of(WithdrawResponse.of(withdrawMemberId)));
	}
}
