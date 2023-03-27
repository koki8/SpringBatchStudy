package com.example.batchstudy.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.URL;

@Configuration
public class AppPropertiesConfig {

    @Value("${downloadURL}")
    private URL downloadUrl;

    @Value("${outputDirectory}")
    private String outputDirectory;

    public URL getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(URL downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }
}
