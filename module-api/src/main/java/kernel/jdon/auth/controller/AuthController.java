package kernel.jdon.auth.controller;

import kernel.jdon.auth.dto.request.RegisterRequest;
import kernel.jdon.auth.dto.response.RegisterResponse;
import kernel.jdon.auth.dto.response.WithdrawResponse;
import kernel.jdon.dto.response.CommonResponse;
import kernel.jdon.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/api/v1/register")
    public ResponseEntity<CommonResponse> register(@RequestBody RegisterRequest registerRequest) {

        log.info("registerRequest: {}", registerRequest);
        Long registerMemberId = memberService.register(registerRequest);
        URI uri = URI.create("/api/v1/member/" + registerMemberId);

        return ResponseEntity.created(uri).body(CommonResponse.of(RegisterResponse.of(registerMemberId)));
    }

    @DeleteMapping("/api/v1/withdraw")
    public ResponseEntity<CommonResponse> withdraw() {

        return ResponseEntity.ok(CommonResponse.of(WithdrawResponse.of(1L)));
    }

    @GetMapping("/api/v1/csrf-token")
    public ResponseEntity<Void> getCsrfToken(CsrfToken csrfToken) {

        return ResponseEntity.noContent().header(csrfToken.getHeaderName(), csrfToken.getToken()).build();
    }
}
