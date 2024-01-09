package kernel.jdon.member.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.dto.response.CommonResponse;
import kernel.jdon.member.dto.request.ModifyMemberRequest;
import kernel.jdon.member.dto.request.NicknameDuplicateRequest;
import kernel.jdon.member.dto.response.GetMemberResponse;
import kernel.jdon.member.dto.response.ModifyMemberResponse;

@RestController
public class MemberController {

	@GetMapping("/api/v1/member")
	public ResponseEntity<CommonResponse> get() {
		GetMemberResponse getMemberResponse = GetMemberResponse.builder()
			.email("aaa@gmail.com")
			.nickname("aaa")
			.birth(LocalDate.now())
			.gender("여성")
			.jobCategoryId(1L)
			.skillList(List.of(1L, 2L, 3L))
			.build();

		return ResponseEntity.ok(CommonResponse.of(getMemberResponse));
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
