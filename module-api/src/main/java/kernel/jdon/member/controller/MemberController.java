package kernel.jdon.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.member.dto.request.NicknameDuplicateRequest;
import kernel.jdon.member.dto.request.UpdateMemberRequest;
import kernel.jdon.member.dto.response.FindMemberResponse;
import kernel.jdon.member.dto.response.UpdateMemberResponse;
import kernel.jdon.member.service.MemberService;
import kernel.jdon.moduleapi.global.annotation.LoginUser;
import kernel.jdon.modulecommon.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	// @GetMapping("/api/v1/member")
	public ResponseEntity<CommonResponse> get(@LoginUser SessionUserInfo sessionUser) {
		Long memberId = sessionUser.getId();
		FindMemberResponse findMemberResponse = memberService.find(memberId);

		return ResponseEntity.ok(CommonResponse.of(findMemberResponse));
	}

	@PutMapping("/api/v1/member")
	public ResponseEntity<CommonResponse> modify(@LoginUser SessionUserInfo user,
		@RequestBody UpdateMemberRequest updateMemberRequest) {
		UpdateMemberResponse updateMemberResponse = memberService.update(user.getId(), updateMemberRequest);

		return ResponseEntity.ok(CommonResponse.of(updateMemberResponse));
	}

	@PostMapping("/api/v1/nickname/duplicate")
	public ResponseEntity<Void> checkNicknameDuplicate(@RequestBody NicknameDuplicateRequest nicknameDuplicateRequest) {
		memberService.checkNicknameDuplicate(nicknameDuplicateRequest.getNickname());

		return ResponseEntity.noContent().build();
	}
}
