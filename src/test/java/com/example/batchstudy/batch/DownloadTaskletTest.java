package com.example.batchstudy.batch;

import com.example.batchstudy.service.DownloadService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DownloadTaskletTest {

    @Mock
    private DownloadService downloadService;

    @Mock
    private StepContribution contribution;

    @Mock
    private ChunkContext chunkContext;

    @InjectMocks
    private DownloadTasklet downloadTasklet;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void execute_正常() throws Exception {
        doNothing().when(downloadService).download();
        downloadTasklet.execute(contribution,chunkContext);
        verify(downloadService).download();
    }
}