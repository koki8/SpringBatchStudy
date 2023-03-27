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
public class BatchTaskletConfig {

    private DownloadTasklet downloadTasklet;

    // Jobの作成に使われる
    private JobBuilderFactory jobBuilderFactory;

    // Stepの作成に使われる。StepはJobの中に一つ以上含まれる。
    private StepBuilderFactory stepBuilderFactory;

    public BatchTaskletConfig(DownloadTasklet downloadTasklet, JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory){
        this.downloadTasklet = downloadTasklet;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    /**
     * downloadTaskletをStepとして登録
     *
     * @return
     */
    @Bean
    public Step step(){
        return stepBuilderFactory.get("step")
                .tasklet(downloadTasklet)
                .build();
    }

    /**
     * 上記で定義したStepをJobとして登録
     *
     *
     * @param step
     * @return
     */
    @Bean
    public Job job(Step step) {
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
