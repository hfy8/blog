package com.blog.message.config;

/**
 * WebSocketConfig
 *
 * @author jeffrey
 * @description
 * @date created in 10:15 2020/12/17
 * @modifyBy
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}