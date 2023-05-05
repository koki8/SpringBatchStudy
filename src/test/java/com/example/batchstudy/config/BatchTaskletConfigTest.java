package com.example.batchstudy.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBatchTest
@SpringBootTest(classes = {BatchTaskletConfig.class})
class BatchTaskletConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    //テスト用のwiremockサーバー
    private WireMockServer mockServer;

    @BeforeEach
    void setUp() {
        //テスト用のwiremockサーバーの起動
        mockServer = new WireMockServer();
        mockServer.start();
    }

    @AfterEach
    void tearDown() {
        //テスト用のwiremockサーバー
        mockServer.stop();
    }

    //Stepテスト
    @Test
    void testStep() {
        //launchStepでStep全体を実行
        JobExecution jobExecution = this.jobLauncherTestUtils.launchStep("step");
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }

    //Jobテスト
    @Test
    void testJob() throws Exception {
        //launchJobでJob全体を実行
        JobExecution jobExecution = this.jobLauncherTestUtils.launchJob();
        assertThat(jobExecution.getExitStatus().getExitCode()).isEqualTo("COMPLETED");
    }

}