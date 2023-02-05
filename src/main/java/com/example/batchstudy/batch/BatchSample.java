package com.example.batchstudy.batch;

import java.io.IOException;

public class BatchSample {
    public static void main(String[] args) throws IOException, InterruptedException {
        String path = "src/main/resources/script/downloadsample.sh";
        ProcessBuilder pb = new ProcessBuilder();
        pb.command(path);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);

        Process process = pb.start();
        int ret = process.waitFor();
        System.out.println(ret);
    }
}
