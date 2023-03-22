package com.example.batchstudy.batch;

import com.example.batchstudy.service.Download;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class DownloadTasklet implements Tasklet {

    private final Download download;

    public DownloadTasklet(Download download) {
        this.download = download;
    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // 郵便番号データダウンロード
        // https://www.post.japanpost.jp/zipcode/dl/oogaki/zip/13tokyo.zip
        URL url = new URL("https://www.post.japanpost.jp/zipcode/dl/oogaki/zip/13tokyo.zip");

        download.download(url);

        return RepeatStatus.FINISHED;
    }
}
