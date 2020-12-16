package com.blog.infomanager.config;

import com.blog.infomanager.inerceptor.FeignBasicAuthRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName FeignSupportConfig
 * @Date 2019/8/18 10:32
 * @Auther bianjie
 **/
@Configuration
public class  FeignSupportConfig {
    @Bean
    public RequestInterceptor requestInterceptor(){
        return new FeignBasicAuthRequestInterceptor();
    }
}
