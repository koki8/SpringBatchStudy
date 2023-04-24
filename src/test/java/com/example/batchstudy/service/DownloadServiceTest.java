package com.example.batchstudy.service;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    //ダウンロード元のURLを指定
    @Value("${downloadURL}")
    URL downloadURL;

    //ダウンロードしたファイルを配置するディレクトリを指定
    @Value("${outputDirectory}")
    String outputDirectory;

//    @Mock
//    private Files filesMock;

    //ダウンロードしたファイルを配置するディレクトリへのPath取得
    Path outputDirectoryPath;

    //ダウンロードしたファイルまでのPath取得
    Path expectedOutputPathObject;

    @Autowired //なぜか省略できない
    public DownloadServiceTest(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);

    @BeforeEach
    void setUp() {
//        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
        //テスト用の出力ファイルと出力先ディレクトリ削除
        try {
            Files.deleteIfExists(expectedOutputPathObject);
            Files.deleteIfExists(outputDirectoryPath);
        } catch (IOException e) {

        }
    }

    @Test
    void download_正常() throws IOException {

        String path = downloadURL.getPath(); //URLのpathを取得　例:http://localhost/path　⇨　/path
        String name = path.substring(path.lastIndexOf("/") + 1); //path部の末尾を取得することでzipファイル名取得　
        String outputPath = outputDirectory + name;

        Path checkOutputPath = Paths.get(outputPath);

//        //Files.copy()メソッドをMock化
//        when(Files.copy(downloadURL.openStream(), checkOutputPath, REPLACE_EXISTING)).thenReturn(9L);

        downloadService.download(downloadURL, outputDirectory);

//        verify(filesMock, times(1)).copy(any(InputStream.class), any(Path.class), any(StandardCopyOption.class));

//        /**
//         * 以下2行はURLの末尾からファイル名を取得するための処理
//         */
//        String path = downloadURL.getPath(); //URLのpathを取得　例:http://localhost/path　⇨　/path
//        String filename = path.substring(path.lastIndexOf("/") + 1); //path部の末尾を取得することでzipファイル名取得　
//
//        outputDirectoryPath = Paths.get(outputDirectory);
//        String expectedOutputPath = outputDirectory + filename;
//        expectedOutputPathObject = Paths.get(expectedOutputPath);
//
//        //ファイルが存在しているかを確認
//        assertThat(Files.exists(expectedOutputPathObject)).isTrue();

    }

}