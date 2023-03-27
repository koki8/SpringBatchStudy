package com.example.batchstudy.controller;

import com.example.batchstudy.service.DownloadService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/download")
public class DownloadSampleController {

    final private DownloadService downloadService;

    public DownloadSampleController (DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping
    public String doDownload() {
        try {
            downloadService.doDownload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "doDownload";
    }
}
