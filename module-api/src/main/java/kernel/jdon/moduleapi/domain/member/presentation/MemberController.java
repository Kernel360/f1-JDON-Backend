package kernel.jdon.moduleapi.domain.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.moduleapi.domain.member.application.MemberFacade;
import kernel.jdon.moduleapi.domain.member.core.MemberCommand;
import kernel.jdon.moduleapi.domain.member.core.MemberInfo;
import kernel.jdon.moduleapi.global.annotation.LoginUser;
import kernel.jdon.modulecommon.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberFacade memberFacade;
	private final MemberDtoMapper memberDtoMapper;

	@GetMapping("/api/v1/member")
	public ResponseEntity<CommonResponse<MemberDto.FindMemberResponse>> getMember(
		@LoginUser final SessionUserInfo member) {
		final Long memberId = member.getId();
		final MemberInfo.FindMemberResponse info = memberFacade.getMember(memberId);
		final MemberDto.FindMemberResponse response = memberDtoMapper.of(info);

		return ResponseEntity.ok(CommonResponse.of(response));
	}

	@PutMapping("/api/v1/member")
	public ResponseEntity<CommonResponse<MemberDto.UpdateMemberResponse>> modifyMember(
		@LoginUser final SessionUserInfo member,
		@RequestBody @Valid final MemberDto.UpdateMemberRequest request) {
		final Long memberId = member.getId();
		final MemberCommand.UpdateMemberRequest command = memberDtoMapper.of(request);
		final MemberInfo.UpdateMemberResponse info = memberFacade.modifyMember(memberId, command);
		final MemberDto.UpdateMemberResponse response = memberDtoMapper.of(info);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

	@PostMapping("/api/v1/nickname/duplicate")
	public ResponseEntity<Void> checkNicknameDuplicate(@RequestBody MemberDto.NicknameDuplicateRequest request) {
		final MemberCommand.NicknameDuplicateRequest command = memberDtoMapper.of(request);
		memberFacade.checkNicknameDuplicate(command);

		return ResponseEntity.noContent().build();
	}
}
