package com.example.batchstudy.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class Download {
    public void download(URL url) throws IOException {

        String path = url.getPath(); //URLのpathを取得　例:http://localhost/path　⇨　/path
        String name = path.substring(path.lastIndexOf("/") + 1); //path部の末尾を取得することでzipファイル名取得
        long size = 0L;

        /**
         *  https://docs.oracle.com/javase/jp/8/docs/api/java/nio/file/Files.html
         *  すべてのバイトを入力ストリームからファイルにコピー
         *  StandardCopyOption.REPLACE_EXISTING　を指定した場合は、
         *  すでにファイルが存在していたら上書きされる
         */
        size = Files.copy(url.openStream(), Paths.get(name), REPLACE_EXISTING);

        System.out.println(name + " - " + size + " bytes ");
    }
}
