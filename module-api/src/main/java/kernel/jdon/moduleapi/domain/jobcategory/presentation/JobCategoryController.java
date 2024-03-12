package kernel.jdon.moduleapi.domain.jobcategory.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kernel.jdon.moduleapi.domain.jobcategory.application.JobCategoryFacade;
import kernel.jdon.moduleapi.domain.jobcategory.core.JobCategoryInfo;
import kernel.jdon.modulecommon.dto.response.CommonResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JobCategoryController {
    private final JobCategoryFacade jobCategoryFacade;
    private final JobCategoryMapper jobCategoryMapper;

    @GetMapping("/api/v1/job-categories")
    public ResponseEntity<CommonResponse<JobCategoryDto.FindJobGroupListResponse>> getJobGroupList() {
        final JobCategoryInfo.FindJobGroupListResponse info = jobCategoryFacade.getJobGroupList();
        final JobCategoryDto.FindJobGroupListResponse response = jobCategoryMapper.of(info);

        return ResponseEntity.ok(CommonResponse.of(response));
    }

}
