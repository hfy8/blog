package com.blog.imgserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ImgserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImgserverApplication.class, args);
    }

}
