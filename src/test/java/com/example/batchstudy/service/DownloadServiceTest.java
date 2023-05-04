package com.example.batchstudy.service;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class DownloadServiceTest {

    private DownloadService downloadService;

    //ダウンロード元のURLを指定（モックサーバーを指定）
    @Value("${downloadURL}")
    URL downloadURL;

    //ダウンロードしたファイルを配置するディレクトリを指定
    @Value("${outputDirectory}")
    String outputDirectory;

    @Autowired //なぜか省略できない
    public DownloadServiceTest(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

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
    void download_正常() throws IOException {

        downloadService.download(downloadURL, outputDirectory);

        String path = downloadURL.getPath(); //URLのpathを取得　例:http://localhost/path　⇨　/path
        String name = path.substring(path.lastIndexOf("/") + 1); //path部の末尾を取得することでzipファイル名取得　
        String outputPath = outputDirectory + name;
        Path checkOutputPath = Paths.get(outputPath);
        assertThat(Files.exists(checkOutputPath)).isTrue(); //指定したディレクトリ配下にファイルが存在しているかを確認

    }


}