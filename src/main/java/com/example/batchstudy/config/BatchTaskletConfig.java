package com.example.batchstudy.config;


import com.example.batchstudy.batch.DownloadTasklet;
import com.example.batchstudy.listener.JobListener;
import com.example.batchstudy.service.DownloadService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.URL;


@Configuration
@EnableBatchProcessing
@ComponentScan("com.example.batchstudy") //JobLauncherTestUtilsの生成に必要
public class BatchTaskletConfig {

    // Jobの作成に使われる
    private JobBuilderFactory jobBuilderFactory;

    // Stepの作成に使われる。StepはJobの中に一つ以上含まれる。
    private StepBuilderFactory stepBuilderFactory;

    //ダウンロード元のURLを指定
    @Value("${downloadURL}")
    URL downloadURL;

    //ダウンロードしたファイルを配置するディレクトリを指定
    @Value("${outputDirectory}")
    String outputDirectory;

    @Autowired
    DownloadService downloadService;

    public BatchTaskletConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    /**
     * DownloadTaskletをBean生成
     *
     * @return
     */
    @Bean
    public Tasklet downloadTasklet() {
        return new DownloadTasklet(downloadService ,downloadURL, outputDirectory);
    }


    /**
     * downloadTaskletをStepとして登録
     *
     * @return
     */
    @Bean
    public Step step() {
        return stepBuilderFactory.get("step")
                .tasklet(downloadTasklet())
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
    public Job job(@Qualifier("step") Step step) {
        return jobBuilderFactory.get("job")
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
