package com.blog.imgserver.config;

/**
 * minioConfig
 *
 * @author jeffrey
 * @description
 * @date created in 17:29 2020/12/7
 * @modifyBy
 */

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@Component
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.port}")
    private int port;

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.secure}")
    private Boolean secure;

    @Value("${minio.bucketName}")
    private String bucketName;


    @Bean("minioClient")
    public MinioClient getMinioClient() throws IOException, NoSuchAlgorithmException, InvalidKeyException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, XmlParserException, ErrorResponseException {
        try {
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(endpoint,port,secure)
                            .credentials(accessKey, secretKey)
                            .build();

            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {
                System.out.println("Bucket 'asiatrip' already exists.");
            }
            return minioClient;
        } catch (MinioException e) {
            throw e;
        }
    }
}