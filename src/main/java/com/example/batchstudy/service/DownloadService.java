package com.example.batchstudy.service;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class DownloadService {

    //ダウンロード元のURLを指定
    private URL downloadURL;

    //ダウンロードしたファイルを配置するディレクトリを指定
    private String outputDirectory;

    public DownloadService(URL downloadURL, String outputDirectory) {
        this.downloadURL = downloadURL;
        this.outputDirectory = outputDirectory;
    }

    /**
     * downloadURLのファイルをダウンロードして、outputDirectoryにファイルを出力する
     *
     * @throws IOException
     */
    public void download() throws IOException {

        /**
         * 以下2行はURLの末尾からファイル名を取得するための処理
         */
        String path = downloadURL.getPath(); //URLのpathを取得　例:http://localhost/path　⇨　/path
        String name = path.substring(path.lastIndexOf("/") + 1); //path部の末尾を取得することでzipファイル名取得　

        String outputPath = outputDirectory + name;

        Path checkOutputPath = Paths.get(outputPath);
        if (!Files.exists(checkOutputPath)) {
            Files.createDirectories(checkOutputPath);
        }

        long size;

        /**
         *  https://docs.oracle.com/javase/jp/8/docs/api/java/nio/file/Files.html
         *  すべてのバイトを入力ストリームからファイルにコピー
         *  StandardCopyOption.REPLACE_EXISTING　を指定した場合は、
         *  すでにファイルが存在していたら上書きされる
         */
        size = Files.copy(downloadURL.openStream(), checkOutputPath, REPLACE_EXISTING);

        System.out.println(outputPath + " - " + size + " bytes ");
    }

}
