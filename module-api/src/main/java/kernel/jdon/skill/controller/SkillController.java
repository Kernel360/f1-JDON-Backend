package kernel.jdon.skill.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.dto.response.CommonResponse;
import kernel.jdon.skill.dto.response.FindCompanyBySkillResponse;
import kernel.jdon.skill.dto.response.FindJdResponse;
import kernel.jdon.skill.dto.response.FindLectureResponse;
import kernel.jdon.skill.dto.response.FindListDataBySkillResponse;
import kernel.jdon.skill.dto.response.FindListHotSkillResponse;
import kernel.jdon.skill.dto.response.FindListJobCategorySkillResponse;
import kernel.jdon.skill.dto.response.FindListMemberSkillResponse;
import kernel.jdon.skill.dto.response.FindMemberSkillResponse;
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
	public ResponseEntity<CommonResponse> getMemberSkillList() {

		List<FindMemberSkillResponse> findMemberSkillResponseList = new ArrayList<>();
		for (long i = 1; i <= 5; i++) {
			FindMemberSkillResponse findMemberSkillResponse = FindMemberSkillResponse.builder()
				.skillId(i)
				.keyword("member_skill_" + i)
				.build();

			findMemberSkillResponseList.add(findMemberSkillResponse);
		}

		FindListMemberSkillResponse findListMemberSkillResponse =
			FindListMemberSkillResponse.builder()
				.skillList(findMemberSkillResponseList)
				.build();

		return ResponseEntity.ok(CommonResponse.of(findListMemberSkillResponse));
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

	@GetMapping("/api/v1/skills/company")
	public ResponseEntity<CommonResponse> getCompanyListBySkill(
		@RequestParam(name = "keyword", defaultValue = "") String keyword) {

		List<FindCompanyBySkillResponse> findCompanyBySkillResponseList = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			FindCompanyBySkillResponse findCompanyBySkillResponse = FindCompanyBySkillResponse.builder()
				.companyName("회사명" + i)
				.imageUrl(
					"https://image.wanted.co.kr/optimize?src=https%3A%2F%2Fstatic.wanted.co.kr%2Fimages%2Fcompany%2F22005%2F1d2vjy100vmqwhux__1080_790.jpg&w=1000&q=75")
				.title("웹 풀스택 개발자 (3년 이상)")
				.detailUrl("https://www.wanted.co.kr/wd/198250")
				.build();

			findCompanyBySkillResponseList.add(findCompanyBySkillResponse);
		}

		return ResponseEntity.ok(CommonResponse.of(findCompanyBySkillResponseList));
	}
}
