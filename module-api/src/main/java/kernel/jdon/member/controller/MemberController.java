package kernel.jdon.member.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.global.annotation.LoginUser;
import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.dto.response.CommonResponse;
import kernel.jdon.member.dto.request.ModifyMemberRequest;
import kernel.jdon.member.dto.request.NicknameDuplicateRequest;
import kernel.jdon.member.dto.response.FindMemberResponse;
import kernel.jdon.member.dto.response.ModifyMemberResponse;
import kernel.jdon.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/api/v1/member")
	public ResponseEntity<CommonResponse> get(@LoginUser SessionUserInfo sessionUser) {
		Long memberId = sessionUser.getId();
		FindMemberResponse findMemberResponse = memberService.find(memberId);

		return ResponseEntity.ok(CommonResponse.of(findMemberResponse));
	}

	@PutMapping("/api/v1/member")
	public ResponseEntity<CommonResponse> modify(@RequestBody ModifyMemberRequest modifyMemberRequest) {
		ModifyMemberResponse modifyMemberResponse = ModifyMemberResponse.builder()
			.email(modifyMemberRequest.getEmail())
			.nickname(modifyMemberRequest.getNickname())
			.birth(modifyMemberRequest.getBirth())
			.gender(modifyMemberRequest.getGender())
			.jobCategoryId(modifyMemberRequest.getJobCategoryId())
			.skillList(modifyMemberRequest.getSkillList())
			.build();

		return ResponseEntity.ok(CommonResponse.of(modifyMemberResponse));
	}

	@PostMapping("nickname/duplicate")
	public ResponseEntity<Void> checkNicknameDuplicate(
		@RequestBody NicknameDuplicateRequest nicknameDuplicateRequest) {

		return ResponseEntity.noContent().build();
	}
}
