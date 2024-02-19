package kernel.jdon.skill.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.dto.response.CommonResponse;
import kernel.jdon.moduleapi.global.annotation.LoginUser;
import kernel.jdon.skill.dto.response.FindListDataBySkillResponse;
import kernel.jdon.skill.dto.response.FindListHotSkillResponse;
import kernel.jdon.skill.dto.response.FindListJobCategorySkillResponse;
import kernel.jdon.skill.dto.response.FindListMemberSkillResponse;
import kernel.jdon.skill.service.SkillService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SkillController {
	private final SkillService skillService;

	@GetMapping("/api/v1/skills/hot")
	public ResponseEntity<CommonResponse> getHotSkillList() {
		FindListHotSkillResponse hotSkillList = skillService.findHotSkillList();

		return ResponseEntity.ok(CommonResponse.of(hotSkillList));
	}

	@GetMapping("/api/v1/skills/member")
	public ResponseEntity<CommonResponse> getMemberSkillList(@LoginUser SessionUserInfo sessionUser) {
		Long memberId = sessionUser.getId();
		FindListMemberSkillResponse findMemberSkillList = skillService.findMemberSkillList(memberId);

		return ResponseEntity.ok(CommonResponse.of(findMemberSkillList));
	}

	@GetMapping("/api/v1/skills/job-category/{jobCategoryId}")
	public ResponseEntity<CommonResponse> getJobCategorySkillList(@PathVariable Long jobCategoryId) {
		FindListJobCategorySkillResponse findListJobCategorySkillResponse =
			skillService.findJobCategorySkillList(jobCategoryId);

		return ResponseEntity.ok(CommonResponse.of(findListJobCategorySkillResponse));
	}

	@GetMapping("/api/v1/skills/search")
	public ResponseEntity<CommonResponse> getDataListBySkill(
		@RequestParam(name = "keyword", defaultValue = "") String keyword,
		@LoginUser SessionUserInfo sessionUser) {
		Long userId = sessionUser == null ? null : sessionUser.getId();
		FindListDataBySkillResponse findListDataBySkillResponse = skillService.findDataBySkillList(keyword, userId);

		return ResponseEntity.ok(CommonResponse.of(findListDataBySkillResponse));
	}
}
