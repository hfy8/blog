package com.blog.message.controller;

import com.blog.message.pojo.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.EncodeException;
import java.io.IOException;

/**
 * WebSocketController
 *
 * @author jeffrey
 * @description
 * @date created in 14:11 2020/12/17
 * @modifyBy
 */

@RestController
@Slf4j
@RequestMapping("message")
public class WebSocketController {
    @Autowired
    private WebSocket userWebSocket;

    /**
     * 模拟给同一个用户的不同客户段发送消息
     * @return
     */
    @PostMapping(value = "/send/user")
    String sendUser(@RequestBody Message message) throws IOException, EncodeException {
        log.info("开始给用户发送消息");
        userWebSocket.sendMessageTo(message);
        return "成功";
    }
}