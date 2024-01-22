package kernel.jdon.jobcategory.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.dto.response.CommonResponse;
import kernel.jdon.jobcategory.dto.response.FindListJobGroupResponse;
import kernel.jdon.jobcategory.service.JobCategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JobCategoryController {
	private final JobCategoryService jobCategoryService;

	@GetMapping("/api/v1/job-categories")
	public ResponseEntity<CommonResponse> getJobGroupList() {
		FindListJobGroupResponse findListJobGroupResponse = jobCategoryService.findJobGroupList();

		return ResponseEntity.ok(CommonResponse.of(findListJobGroupResponse));
	}

}
