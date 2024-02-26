package kernel.jdon.moduleapi.domain.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	public ResponseEntity<CommonResponse<MemberDto.FindMemberResponse>> get(@LoginUser SessionUserInfo member) {
		final Long memberId = member.getId();
		final MemberInfo.FindMemberResponse info = memberFacade.find(memberId);
		final MemberDto.FindMemberResponse response = memberDtoMapper.of(info);

		return ResponseEntity.ok(CommonResponse.of(response));
	}

	@PutMapping("/api/v1/member")
	public ResponseEntity<CommonResponse<MemberDto.UpdateMemberResponse>> modify(@LoginUser SessionUserInfo member,
		@RequestBody @Valid MemberDto.UpdateMemberRequest request) {
		final Long memberId = member.getId();
		final MemberCommand.UpdateMemberRequest command = memberDtoMapper.of(request);
		final MemberInfo.UpdateMemberResponse info = memberFacade.update(memberId, command);
		final MemberDto.UpdateMemberResponse response = memberDtoMapper.of(info);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}
}
