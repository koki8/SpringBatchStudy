package com.example.batchstudy.config;


import com.example.batchstudy.batch.DownloadTasklet;
import com.example.batchstudy.listener.JobListener;
import com.example.batchstudy.service.DownloadService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.net.URL;


@Configuration
@EnableBatchProcessing
@ComponentScan("com.example.batchstudy") //JobLauncherTestUtilsの生成に必要
public class BatchTaskletConfig {

    @Bean
    public DownloadService downloadService(@Value("${downloadURL}")URL downloadURL, String outputDirectory) {
        return new DownloadService(downloadURL, outputDirectory);
    }


    /**
     * DownloadTaskletをBean生成
     *
     * @return
     */
    @Bean
    public Tasklet downloadTasklet(DownloadService downloadService) {
        return new DownloadTasklet(downloadService);
    }


    /**
     * downloadTaskletをStepとして登録
     *
     * @return
     */
    @Bean
    public Step step(JobRepository jobRepository, Tasklet downloadTasklet, PlatformTransactionManager transactionManager) {
        return new StepBuilder("step", jobRepository)
                .tasklet(downloadTasklet, transactionManager)
                .build();
    }

    /**
     * 上記で定義したStepをJobとして登録
     *
     * @param step
     * @return
     */
    @Bean
    //https://qiita.com/lukaliao/items/46330ec865662da6d6f3
    //StepのTestのためには、@QualifierでBeanを強く特定した方がいいらしい
    public Job job(JobRepository jobRepository, @Qualifier("step") Step step) {
        return new JobBuilder("job", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .start(step)
                .build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new JobListener();
    }

}
