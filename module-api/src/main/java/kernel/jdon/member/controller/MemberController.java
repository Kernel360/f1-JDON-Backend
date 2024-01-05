package kernel.jdon.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.dto.response.CommonResponse;
import kernel.jdon.member.dto.request.ModifyMemberRequest;
import kernel.jdon.member.dto.request.SaveMemberRequest;
import kernel.jdon.member.dto.response.ModifyMemberResponse;
import kernel.jdon.member.dto.response.SaveMemberResponse;

@RestController
@RequestMapping("/api/v1")
public class MemberController {

	@PostMapping("/member")
	public ResponseEntity<CommonResponse> save(@RequestBody SaveMemberRequest saveMemberRequest) {
		return ResponseEntity.ok(CommonResponse.of(SaveMemberResponse.of(1L)));
	}

	@PutMapping("/member")
	public ResponseEntity<CommonResponse> modify(@RequestBody ModifyMemberRequest modifyMemberRequest) {
		ModifyMemberResponse modifyMemberResponse = ModifyMemberResponse.builder().email(modifyMemberRequest.getEmail())
			.nickname(modifyMemberRequest.getNickname())
			.birth(modifyMemberRequest.getBirth())
			.gender(modifyMemberRequest.getGender())
			.jobCategoryId(modifyMemberRequest.getJobCategoryId())
			.skillList(modifyMemberRequest.getSkillList())
			.build();
		return ResponseEntity.ok(CommonResponse.of(modifyMemberResponse));
	}
}
