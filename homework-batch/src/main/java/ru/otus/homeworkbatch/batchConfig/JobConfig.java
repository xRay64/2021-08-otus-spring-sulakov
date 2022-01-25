package ru.otus.homeworkbatch.batchConfig;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.lang.NonNull;
import ru.otus.homeworkbatch.service.MongoCleanUpService;


@Configuration
@RequiredArgsConstructor
public class JobConfig {
    private final Logger logger = LoggerFactory.getLogger("BookBatchTransferJob");

    public static final String JOB_NAME = "bookImportJob";

    private final MongoCleanUpService mongoCleanUpService;
    private final JobBuilderFactory jobBuilderFactory;


    @Bean
    public Job importUserJob(Step cleanUpMongo, Flow authorGenreSplitFlow, Step bookTransferStep) {
        return jobBuilderFactory.get(JOB_NAME)
                .incrementer(new RunIdIncrementer())
                .flow(cleanUpMongo)
                .next(authorGenreSplitFlow)
                .next(bookTransferStep)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        logger.info("Начало {} job", JOB_NAME);
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        logger.info("Конец {} job", JOB_NAME);
                    }
                })
                .build();
    }

    @Bean
    public Flow authorGenreSplitFlow(Flow authorFlow, Flow genreFlow) {
        return new FlowBuilder<SimpleFlow>("authorGenreSplitFlow")
                .split(new SimpleAsyncTaskExecutor("authorGenreExecutor"))
                .add(authorFlow, genreFlow)
                .build();
    }

    @Bean
    public Flow authorFlow(Step authorTransferStep) {
        return new FlowBuilder<SimpleFlow>("authorStepFlow")
                .start(authorTransferStep)
                .build();
    }

    @Bean
    public Flow genreFlow(Step genreTransferStep) {
        return new FlowBuilder<SimpleFlow>("genreStepFlow")
                .start(genreTransferStep)
                .build();
    }

    @Bean
    public MethodInvokingTaskletAdapter cleanUpMongoTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();

        adapter.setTargetObject(mongoCleanUpService);
        adapter.setTargetMethod("cleanUpMongo");

        return adapter;
    }

    @Bean
    public Step cleanUpMongo(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("cleanUpMongoStep")
                .tasklet(cleanUpMongoTasklet())
                .build();
    }
}
