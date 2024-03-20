package kernel.jdon.modulebatch.job.jd.reader.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WantedJobDetailListResponse {
    private final List<WantedJobDetailResponse> jobDetailList;
}
