package com.example.batchstudy.controller;

import com.example.batchstudy.service.DownloadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;

@RestController
@RequestMapping("/download")
public class DownloadSampleController {

    private final DownloadService downloadService;

    //ダウンロード元のURLを指定
    URL downloadURL;

    //ダウンロードしたファイルを配置するディレクトリを指定
    String outputDirectory;

    public DownloadSampleController (DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping
    public String doDownload() {
        try {
            downloadService.download(downloadURL, outputDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "doDownload";
    }
}
