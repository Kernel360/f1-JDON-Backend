package kernel.jdon.modulebatch.job.jd.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.job.jd.reader.condition.JobSearchJobPosition;
import kernel.jdon.modulebatch.job.jd.reader.dto.PartJobDetailListInfo;
import kernel.jdon.modulebatch.job.jd.reader.dto.WantedJobDetailListResponse;
import kernel.jdon.modulebatch.job.jd.reader.fetchmanager.JobListFetchManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class PartBackendWantedJdItemReader implements ItemReader<WantedJobDetailListResponse> {
    private final WantedJdClient wantedJdClient;
    private final JobListFetchManager jobListFetchManager;
    private boolean isLimit;

    @Override
    public WantedJobDetailListResponse read() throws
        Exception,
        UnexpectedInputException,
        ParseException,
        NonTransientResourceException {
        /** 이전 Chunk의 반환 데이터가 중복된 데이터에 의해 종료됐다면 Job 종료 **/
        if (isLimit) {
            return null;
        }

        final JobSearchJobPosition jobPosition = JobSearchJobPosition.JOB_POSITION_SERVER;
        final PartJobDetailListInfo info = wantedJdClient.getPartJobDetailList(jobPosition,
            jobListFetchManager.getOffset());

        if (info.isMaxDuplicate()) {
            isLimit = true;
        }

        jobListFetchManager.incrementOffset();

        return !info.getJobDetailList().isEmpty() ? new WantedJobDetailListResponse(info.getJobDetailList()) : null;
    }
}

