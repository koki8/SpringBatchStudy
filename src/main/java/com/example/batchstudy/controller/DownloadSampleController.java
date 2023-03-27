package com.example.batchstudy.controller;

import com.example.batchstudy.service.Download;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/download")
public class DownloadSampleController {

    final private Download download;

    public DownloadSampleController (Download download) {
        this.download = download;
    }

    @GetMapping
    public String doDownload() {
        try {
            download.doDownload();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "doDownload";
    }
}
