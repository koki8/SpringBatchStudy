package com.example.batchstudy.controller;

import com.example.batchstudy.service.DownloadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;

@RestController
@RequestMapping("/download")
public class DownloadSampleController {

    //ダウンロード元のURLを指定
    @Value("${downloadURL}")
    URL downloadURL;

    //ダウンロードしたファイルを配置するディレクトリを指定
    @Value("${outputDirectory}")
    String outputDirectory;



    @GetMapping
    public String doDownload() {
        DownloadService downloadService = new DownloadService(downloadURL, outputDirectory);
        try {
            downloadService.download();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "doDownload";
    }
}
