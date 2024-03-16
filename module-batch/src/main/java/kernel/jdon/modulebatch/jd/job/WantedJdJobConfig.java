package kernel.jdon.modulebatch.jd.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import kernel.jdon.modulebatch.jd.processor.WantedJdItemProcessor;
import kernel.jdon.modulebatch.jd.reader.WantedJdItemReader;
import kernel.jdon.modulebatch.jd.reader.dto.WantedJdList;
import kernel.jdon.modulebatch.jd.reader.dto.WantedJobDetailListResponse;
import kernel.jdon.modulebatch.jd.writer.WantedJdItemWriter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WantedJdJobConfig {
    private final WantedJdItemReader wantedJdItemReader;
    private final WantedJdItemProcessor wantedJdItemProcessor;
    private final WantedJdItemWriter wantedJdItemWriter;

    @Bean
    public Job wantedJdJob(JobRepository jobRepository, Step wantedJdScrapingStep) {
        return new JobBuilder("wantedJdJob", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(wantedJdScrapingStep)
            .build();
    }

    @Bean
    @JobScope
    public Step wantedJdStep(JobRepository jobRepository,
        PlatformTransactionManager platformTransactionManager,
        @Value("#{jobParameters[offset]}") int offset) {
        return new StepBuilder("wantedJdStep", jobRepository)
            .<WantedJobDetailListResponse, WantedJdList>chunk(3, platformTransactionManager)
            // 전달받은 jobParameter offset을 증가시켜서 Reader에 계속 전달해야한다.
            // .reader(wantedJdItemReader)
            //.reader(new WantedJdItemReader(offset))
            .processor(wantedJdItemProcessor)
            .writer(wantedJdItemWriter)
            .build();
    }

}
