package com.blog.infomanager;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@MapperScan("com.blog.infomanager.maps")
@EnableAuthorizationServer
@EnableDiscoveryClient
@EnableResourceServer
public class InfomanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfomanagerApplication.class, args);
    }

}
