package kernel.jdon.auth.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.auth.dto.request.RegisterRequest;
import kernel.jdon.auth.dto.response.RegisterResponse;
import kernel.jdon.auth.dto.response.WithdrawResponse;
import kernel.jdon.dto.response.CommonResponse;
import kernel.jdon.member.service.MemberService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

	private final MemberService memberService;

	@PostMapping("/api/v1/register")
	public ResponseEntity<CommonResponse> register(@RequestBody RegisterRequest registerRequest) {

		Long memberId = memberService.register(registerRequest);
		URI uri = URI.create("/api/v1/member/" + memberId);

		return ResponseEntity.created(uri).body(CommonResponse.of(RegisterResponse.of(memberId)));
	}

	@DeleteMapping("/api/v1/withdraw")
	public ResponseEntity<CommonResponse> withdraw() {

		return ResponseEntity.ok(CommonResponse.of(WithdrawResponse.of(1L)));
	}
}
