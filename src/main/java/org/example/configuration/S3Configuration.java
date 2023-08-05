package org.example.configuration;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class S3Configuration {
    @Autowired
    private AWSCredentialsProvider credentialsProvider;

    @Value("${cloud.aws.region.static}")
    private String awsRegion;

    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(this.credentialsProvider)
                .withRegion(this.awsRegion)
                .build();
    }
}
