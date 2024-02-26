package kernel.jdon.moduleapi.domain.member.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.moduleapi.domain.member.application.MemberFacade;
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
	public ResponseEntity<CommonResponse<MemberDto.FindMemberResponse>> get(@LoginUser SessionUserInfo sessionUser) {
		final Long memberId = sessionUser.getId();
		final MemberInfo.FindMemberResponse info = memberFacade.find(memberId);
		final MemberDto.FindMemberResponse response = memberDtoMapper.of(info);

		return ResponseEntity.ok(CommonResponse.of(response));
	}
}
