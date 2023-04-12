package com.example.batchstudy.config;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBatchTest
@SpringBootTest(classes = {BatchTaskletConfig.class})
class BatchTaskletConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    //Jobテスト
    @Test
    void testJob() throws Exception {

        //launchJobでJob全体を実行
        JobExecution jobExecution = this.jobLauncherTestUtils.launchJob();
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

    }

    //Stepテスト
    @Test
    void testStep() {

        //launchStepでStep全体を実行
        JobExecution jobExecution = this.jobLauncherTestUtils.launchStep("step");
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");

    }

}