package com.example.batchstudy.service;

import com.example.batchstudy.property.AppProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DownloadServiceTest {

    //DownloadServiceで使われているAppPropertiesをモック化
    private final AppProperties appPropertiesMock = mock(AppProperties.class);

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void doDownload_正常() throws IOException {
        DownloadService downloadService = new DownloadService(appPropertiesMock);
        URL inputURL = new URL("https://www.post.japanpost.jp/zipcode/dl/oogaki/zip/13tokyo.zip");
        String outputDirectory = "outputTest/";
        Path outputDirectoryPath = Paths.get(outputDirectory);

        String expectedOutputPath = outputDirectory + "13tokyo.zip";
        Path expectedOutputPathObject = Paths.get(expectedOutputPath);

        // AppPropertiesのモック化
        when(appPropertiesMock.getDownloadUrl()).thenReturn(inputURL);
        when(appPropertiesMock.getOutputDirectory()).thenReturn(outputDirectory);

        downloadService.doDownload();

        //doDownload実行時にAppPropertiesから情報を取得して、doDownloadが実行されているかを確認
        verify(appPropertiesMock).getDownloadUrl();
        verify(appPropertiesMock).getOutputDirectory();

        //テスト用の出力先ディレクトリ削除
        Files.deleteIfExists(expectedOutputPathObject);
        Files.deleteIfExists(outputDirectoryPath);

    }

}