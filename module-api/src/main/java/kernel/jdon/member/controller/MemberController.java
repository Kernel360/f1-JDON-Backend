package kernel.jdon.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.dto.response.CommonResponse;
import kernel.jdon.member.dto.request.SaveMemberRequest;
import kernel.jdon.member.dto.response.SaveMemberResponse;

@RestController
@RequestMapping("/api/v1")
public class MemberController {

	@PostMapping("/member")
	public ResponseEntity<CommonResponse> save(@RequestBody SaveMemberRequest saveMemberRequest) {
		return ResponseEntity.ok(CommonResponse.of(SaveMemberResponse.of(1L)));
	}
}
