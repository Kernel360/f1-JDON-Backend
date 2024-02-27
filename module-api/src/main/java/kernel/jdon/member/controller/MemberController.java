package kernel.jdon.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import kernel.jdon.member.dto.request.NicknameDuplicateRequest;
import kernel.jdon.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	// @PostMapping("/api/v1/nickname/duplicate")
	public ResponseEntity<Void> checkNicknameDuplicate(@RequestBody NicknameDuplicateRequest nicknameDuplicateRequest) {
		memberService.checkNicknameDuplicate(nicknameDuplicateRequest.getNickname());

		return ResponseEntity.noContent().build();
	}
}
