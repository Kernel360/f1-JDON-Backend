package kernel.jdon.modulebatch.job.course;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import kernel.jdon.modulebatch.job.course.dto.InflearnCourseAndSkillKeywordInfo;
import kernel.jdon.modulebatch.job.course.listener.InflearnJobExecutionListener;
import kernel.jdon.modulebatch.job.course.processor.InflearnCourseItemProcessor;
import kernel.jdon.modulebatch.job.course.reader.InflearnCourseItemReader;
import kernel.jdon.modulebatch.job.course.writer.InflearnCourseItemWriter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class InflearnCourseScrapingJobConfig {

    private static final int CHUNK_SIZE = 1;

    private final PlatformTransactionManager platformTransactionManager;
    private final InflearnJobExecutionListener inflearnJobExecutionListener;
    private final InflearnCourseItemReader inflearnCourseItemReader;
    private final InflearnCourseItemProcessor inflearnCourseItemProcessor;
    private final InflearnCourseItemWriter inflearnCourseItemWriter;

    @Bean
    public Job inflearnCourseScrapingJob(JobRepository jobRepository) {
        return new JobBuilder("inflearnCourseScrapingJob", jobRepository)
            .listener(inflearnJobExecutionListener)
            .start(inflearnCourseScrapingStep(jobRepository))
            .build();
    }

    @Bean
    @JobScope
    public Step inflearnCourseScrapingStep(JobRepository jobRepository) {
        return new StepBuilder("inflearnCourseScrapingStep", jobRepository)
            .<String, InflearnCourseAndSkillKeywordInfo>chunk(CHUNK_SIZE, platformTransactionManager)
            .reader(inflearnCourseItemReader)
            .processor(inflearnCourseItemProcessor)
            .writer(inflearnCourseItemWriter)
            .allowStartIfComplete(true)
            .build();
    }
}
