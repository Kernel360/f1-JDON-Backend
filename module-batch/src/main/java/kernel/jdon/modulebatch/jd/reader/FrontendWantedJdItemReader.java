package kernel.jdon.modulebatch.jd.reader;

import java.util.List;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import kernel.jdon.modulebatch.jd.reader.counter.JobListFetchManager;
import kernel.jdon.modulebatch.jd.reader.dto.WantedJobDetailListResponse;
import kernel.jdon.modulebatch.jd.reader.dto.WantedJobDetailResponse;
import kernel.jdon.modulebatch.jd.search.JobSearchJobPosition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class FrontendWantedJdItemReader implements ItemReader<WantedJobDetailListResponse> {
    private final WantedJdClient wantedJdClient;
    private final JobListFetchManager jobListFetchManager;

    @Override
    public WantedJobDetailListResponse read() throws
        Exception,
        UnexpectedInputException,
        ParseException,
        NonTransientResourceException {
        final JobSearchJobPosition jobPosition = JobSearchJobPosition.JOB_POSITION_FRONTEND;
        final List<WantedJobDetailResponse> jobDetailList = wantedJdClient.getJobDetailList(jobPosition,
            jobListFetchManager.getOffset());

        jobListFetchManager.incrementOffset();

        return !jobDetailList.isEmpty() ? new WantedJobDetailListResponse(jobDetailList) : null;
    }

}
