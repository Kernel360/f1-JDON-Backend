package kernel.jdon.modulebatch.job.jd.reader.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PartJobDetailListInfo {
    private boolean isMaxDuplicate;
    private List<WantedJobDetailResponse> jobDetailList;
}
