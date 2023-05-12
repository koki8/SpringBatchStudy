package com.example.batchstudy.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class DownloadServiceTest {

    private DownloadService downloadService;

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

    @Test
    void download_正常系(@Value("${downloadURL}")URL downloadURL, @Value("${outputDirectory}")String outputDirectory) throws IOException {
        DownloadService downloadService = new DownloadService(downloadURL, outputDirectory);
        downloadService.download();

        String path = downloadURL.getPath(); //URLのpathを取得　例:http://localhost/path　⇨　/path
        String name = path.substring(path.lastIndexOf("/") + 1); //path部の末尾を取得することでzipファイル名取得　
        String outputPath = outputDirectory + name;
        Path checkOutputPath = Paths.get(outputPath);
        assertThat(Files.exists(checkOutputPath)).isTrue(); //指定したディレクトリ配下にファイルが存在しているかを確認

    }

    @Test
    void download_異常系(@Value("${outputDirectory}")String outputDirector) {
        DownloadService downloadService = new DownloadService(null, outputDirector);
        assertThatThrownBy(() -> downloadService.download())
                .isInstanceOf(Exception.class);
    }

}