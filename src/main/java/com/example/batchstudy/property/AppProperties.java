package com.example.batchstudy.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@ConstructorBinding //セッターの定義が不要になる
public class AppProperties {

    @Value("${downloadURL}")
    private URL downloadUrl;

    @Value("${outputDirectory}")
    private String outputDirectory;

    public URL getDownloadUrl() {
        return downloadUrl;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

}
