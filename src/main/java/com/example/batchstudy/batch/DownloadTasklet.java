package com.example.batchstudy.batch;

import com.example.batchstudy.service.DownloadService;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.net.URL;

/**
 * downloadをTaskletとして定義
 */
public class DownloadTasklet implements Tasklet {

    //ダウンロード元のURLを指定
    URL downloadURL;

    //ダウンロードしたファイルを配置するディレクトリを指定
    String outputDirectory;

    DownloadService downloadService;

    public DownloadTasklet(DownloadService downloadService, URL downloadURL, String outputDirectory) {
        this.downloadService = downloadService;
        this.downloadURL = downloadURL;
        this.outputDirectory = outputDirectory;
    }


    /**
     * ジョブ/ステップで呼び出されると実行され、戻りに設定したRepeatStatus.FINISHEDかエラーを投げることで終了する
     *
     * @param contribution
     * @param chunkContext
     * @return
     * @throws Exception
     */
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        downloadService.download(downloadURL, outputDirectory);
        return RepeatStatus.FINISHED;
    }
}
