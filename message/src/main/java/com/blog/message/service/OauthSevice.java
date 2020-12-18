package com.blog.message.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * OauthSevice
 *
 * @author jeffrey
 * @description
 * @date created in 13:55 2020/12/18
 * @modifyBy
 */
@FeignClient("blog-auth-service")
public interface OauthSevice {

    @RequestMapping(value = "/oauth/check_token")
    Map<String, ?> checkToken(@RequestParam("token") String value);
}
