package com.example.batchstudy.batch;

import com.example.batchstudy.service.DownloadService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
class DownloadTaskletTest {

    @Mock
    private DownloadService downloadService;

    @Mock
    private StepContribution contribution;

    @Mock
    private ChunkContext chunkContext;

    //ダウンロード元のURLを指定
    private URL downloadURL;

    //ダウンロードしたファイルを配置するディレクトリを指定
    private String outputDirectory;

    @InjectMocks
    private DownloadTasklet downloadTasklet;

    @Test
    void execute_正常() throws Exception {
//        downloadTasklet.execute(contribution,chunkContext);
//        verify(downloadService).download(downloadURL,outputDirectory);
    }
}