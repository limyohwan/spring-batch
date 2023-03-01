package com.example.SpringBatchTutorial.job.ConditionalStep;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ConditionalStepJobConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job conditionalStepJob(Step conditionalStartStep, Step conditionalAllStep, Step conditionalFailStep, Step conditionalCompletedStep){
        return jobBuilderFactory.get("conditionalStepJob")
                .incrementer(new RunIdIncrementer())
                .start(conditionalStartStep)
                .on("FAILED").to(conditionalFailStep)
                .from(conditionalStartStep)
                .on("COMPLETED").to(conditionalCompletedStep)
                .from(conditionalStartStep)
                .on("*").to(conditionalAllStep)
                .end()
                .build();
    }

    @JobScope
    @Bean
    public Step conditionalStartStep(){
        return stepBuilderFactory.get("conditionalStartStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("conditional start step");
                    return RepeatStatus.FINISHED;
//                    throw new Exception("ex");
                })
                .build();
    }

    @JobScope
    @Bean
    public Step conditionalAllStep(){
        return stepBuilderFactory.get("conditionalAllStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("conditional all step");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @JobScope
    @Bean
    public Step conditionalFailStep(){
        return stepBuilderFactory.get("conditionalFailStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("conditional fail step");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @JobScope
    @Bean
    public Step conditionalCompletedStep(){
        return stepBuilderFactory.get("conditionalCompletedStep")
                .tasklet((contribution, chunkContext) -> {
                    System.out.println("conditional completed step");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
