package kernel.jdon.modulebatch.job.jd.reader;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.job.jd.reader.condition.JobSearchJobPosition;
import kernel.jdon.modulebatch.job.jd.reader.dto.WantedJobDetailListResponse;
import kernel.jdon.modulebatch.job.jd.reader.dto.WantedJobDetailResponse;
import kernel.jdon.modulebatch.job.jd.reader.fetchmanager.JobListFetchManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class AllBackendWantedJdItemReader implements ItemReader<WantedJobDetailListResponse> {
    private final WantedJdClient wantedJdClient;
    private final JobListFetchManager jobListFetchManager;

    @Override
    public WantedJobDetailListResponse read() throws
        Exception,
        UnexpectedInputException,
        ParseException,
        NonTransientResourceException {
        final JobSearchJobPosition jobPosition = JobSearchJobPosition.JOB_POSITION_SERVER;
        final List<WantedJobDetailResponse> jdList = wantedJdClient.getJdList(jobPosition,
            jobListFetchManager.getOffset());

        jobListFetchManager.incrementOffset();

        return !jdList.isEmpty() ? new WantedJobDetailListResponse(jdList) : null;
    }
}
