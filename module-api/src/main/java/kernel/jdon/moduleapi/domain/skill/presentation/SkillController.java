package kernel.jdon.moduleapi.domain.skill.presentation;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.moduleapi.domain.skill.application.SkillFacade;
import kernel.jdon.moduleapi.domain.skill.core.SkillInfo;
import kernel.jdon.moduleapi.global.annotation.LoginUser;
import kernel.jdon.moduleapi.global.dto.SessionUserInfo;
import kernel.jdon.modulecommon.dto.response.CommonResponse;
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
		@LoginUser final SessionUserInfo member) {
		final SkillInfo.FindMemberSkillListResponse info = skillFacade.getMemberSkillList(member.getId());
		final SkillDto.FindMemberSkillListResponse response = skillDtoMapper.of(info);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

	@GetMapping("/api/v1/skills/job-category/{jobCategoryId}")
	public ResponseEntity<CommonResponse<SkillDto.FindJobCategorySkillListResponse>> getJobCategorySkillList(
		@PathVariable final Long jobCategoryId) {
		final SkillInfo.FindJobCategorySkillListResponse info = skillFacade.getJobCategorySkillList(jobCategoryId);
		final SkillDto.FindJobCategorySkillListResponse response = skillDtoMapper.of(info);

		return ResponseEntity.ok().body(CommonResponse.of(response));
	}

	@GetMapping("/api/v1/skills/search")
	public ResponseEntity<CommonResponse<SkillDto.FindDataListBySkillResponse>> getLectureListAndJdListBySkill(
		@RequestParam(name = "keyword", defaultValue = "") final String keyword,
		@LoginUser final SessionUserInfo member) {
		final Long memberId = getSessionUserId(member);
		final SkillInfo.FindDataListBySkillResponse info = skillFacade.getDataListBySkill(keyword, memberId);
		final SkillDto.FindDataListBySkillResponse response = skillDtoMapper.of(info);

		return ResponseEntity.ok(CommonResponse.of(response));
	}

	private Long getSessionUserId(SessionUserInfo member) {
		return Optional.ofNullable(member)
			.map(session -> member.getId())
			.orElse(null);
	}
}
