package com.example.batchstudy.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class DownloadServiceTest {

    private final DownloadService downloadService;

    //ダウンロード元のURLを指定
    @Value("${downloadURL}")
    URL downloadURL;

    //ダウンロードしたファイルを配置するディレクトリを指定
    @Value("${outputDirectory}")
    String outputDirectory;

    //ダウンロードしたファイルを配置するディレクトリへのPath取得
    Path outputDirectoryPath;

    //ダウンロードしたファイルまでのPath取得
    Path expectedOutputPathObject;

    @Autowired //なぜか省略できない
    public DownloadServiceTest(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @BeforeEach
    void setUp() {

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

        downloadService.download();

        /**
         * 以下2行はURLの末尾からファイル名を取得するための処理
         */
        String path = downloadURL.getPath(); //URLのpathを取得　例:http://localhost/path　⇨　/path
        String filename = path.substring(path.lastIndexOf("/") + 1); //path部の末尾を取得することでzipファイル名取得　

        outputDirectoryPath = Paths.get(outputDirectory);
        String expectedOutputPath = outputDirectory + filename;
        expectedOutputPathObject = Paths.get(expectedOutputPath);

        //ファイルが存在しているかを確認
        assertThat(Files.exists(expectedOutputPathObject)).isTrue();

    }

}