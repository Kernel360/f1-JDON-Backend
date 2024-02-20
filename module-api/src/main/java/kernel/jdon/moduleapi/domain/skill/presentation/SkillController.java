package kernel.jdon.moduleapi.domain.skill.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.modulecommon.dto.response.CommonResponse;
import kernel.jdon.moduleapi.domain.skill.application.SkillFacade;
import kernel.jdon.moduleapi.domain.skill.core.SkillInfo;
import kernel.jdon.moduleapi.global.annotation.LoginUser;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SkillController {
	private final SkillFacade skillFacade;
	private final SkillDtoMapper skillDtoMapper;

	@GetMapping("/api/v1/skills/hot")
	public ResponseEntity<CommonResponse<SkillDto.FindHotSkillListResponse>> getHotSkillList() {
		final SkillInfo.FindHotSkillListResponse info = skillFacade.getHotSkillList();
		final SkillDto.FindHotSkillListResponse response = skillDtoMapper.of(info);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

	@GetMapping("/api/v1/skills/member")
	public ResponseEntity<CommonResponse<SkillDto.FindMemberSkillListResponse>> getMemberSkillList(
		final @LoginUser SessionUserInfo sessionUser) {
		final SkillInfo.FindMemberSkillListResponse info = skillFacade.getMemberSkillList(sessionUser.getId());
		final SkillDto.FindMemberSkillListResponse response = skillDtoMapper.of(info);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

}
