package com.example.batchstudy.config;


import com.example.batchstudy.batch.DownloadTasklet;
import com.example.batchstudy.listener.JobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableBatchProcessing
public class BatchTasklet {

    private DownloadTasklet downloadTasklet;
    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    public BatchTasklet(DownloadTasklet downloadTasklet, JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory){
        this.downloadTasklet = downloadTasklet;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Step step(){
        return stepBuilderFactory.get("step")
                .tasklet(downloadTasklet)
                .build();
    }

    public Job job(Step step) throws Exception {
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .start(step)
                .build();
    }

    @Bean
    public JobExecutionListener listener(){
        return new JobListener();
    }



}
