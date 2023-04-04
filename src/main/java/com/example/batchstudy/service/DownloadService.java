package com.example.batchstudy.service;

import com.example.batchstudy.property.AppProperties;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class DownloadService {

    private final AppProperties appProperties;

    public DownloadService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    /**
     * inputURLのファイルをダウンロードして、outputDirectoryにファイルを出力する
     *
     * @param inputURL
     * @param outputDirectory
     * @throws IOException
     */
    public void download(URL inputURL, String outputDirectory) throws IOException {

        /**
         * 以下2行はURLの末尾からファイル名を取得するための処理
         */
        String path = inputURL.getPath(); //URLのpathを取得　例:http://localhost/path　⇨　/path
        String name = path.substring(path.lastIndexOf("/") + 1); //path部の末尾を取得することでzipファイル名取得　

        String outputPath = outputDirectory + name;

        Path checkOutputPath = Paths.get(outputPath);
        if (!Files.exists(checkOutputPath)) {
            Files.createDirectories(checkOutputPath);
        }

        long size = 0L;

        /**
         *  https://docs.oracle.com/javase/jp/8/docs/api/java/nio/file/Files.html
         *  すべてのバイトを入力ストリームからファイルにコピー
         *  StandardCopyOption.REPLACE_EXISTING　を指定した場合は、
         *  すでにファイルが存在していたら上書きされる
         */
        size = Files.copy(inputURL.openStream(), checkOutputPath, REPLACE_EXISTING);

        System.out.println(outputPath + " - " + size + " bytes ");
    }

    /**
     * AppPropertiesConfigで設定されている内容に基づいて、downloadメソッドを実行
     *
     * @throws IOException
     */
    public void doDownload() throws IOException {
        URL downloadUrl = appProperties.getDownloadUrl();
        String outputDirectory = appProperties.getOutputDirectory();
        download(downloadUrl, outputDirectory);
    }
}
