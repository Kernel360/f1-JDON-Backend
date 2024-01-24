package kernel.jdon.skill.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.auth.dto.SessionUserInfo;
import kernel.jdon.dto.response.CommonResponse;
import kernel.jdon.global.annotation.LoginUser;
import kernel.jdon.skill.dto.response.FindJdResponse;
import kernel.jdon.skill.dto.response.FindLectureResponse;
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
		@RequestParam(name = "keyword", defaultValue = "") String keyword) {

		List<FindLectureResponse> findLectureResponseList = new ArrayList<>();
		List<FindJdResponse> findJdResponseList = new ArrayList<>();
		for (long i = 1; i <= 3; i++) {
			FindLectureResponse findLectureResponse = FindLectureResponse.builder()
				.lectureId(i)
				.title("스프링부트 고급편_" + i)
				.lectureUrl("www.inflearn.com/we234")
				.imageUrl(
					"https://cdn.inflearn.com/public/courses/327260/cover/a51a5154-c375-4210-9fb1-0b716fd4ac73/327260-eng.png")
				.instructor("김영한")
				.studentCount(5332)
				.price(180000)
				.isFavorite(false)
				.build();

			findLectureResponseList.add(findLectureResponse);
		}

		for (long i = 1; i <= 6; i++) {
			FindJdResponse findJdResponse = FindJdResponse.builder()
				.company("트렌비_" + i)
				.title("백엔드개발자_" + i)
				.imageUrl("https://www.amazon.s3.sdkjfhwk.dkjfhwkjdh")
				.jdUrl("https://www.wanted.co.kr/wd/196444")
				.build();

			findJdResponseList.add(findJdResponse);
		}

		FindListDataBySkillResponse findListDataBySkillResponse =
			FindListDataBySkillResponse.builder()
				.lectureList(findLectureResponseList)
				.jdList(findJdResponseList)
				.build();

		return ResponseEntity.ok(CommonResponse.of(findListDataBySkillResponse));
	}
}
