package com.blog.message.controller;

/**
 * socketApi
 *
 * @author jeffrey
 * @description
 * @date created in 10:18 2020/12/17
 * @modifyBy
 */

import com.alibaba.fastjson.JSON;
import com.blog.message.pojo.Message;
import com.blog.message.service.OauthSevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/connectWebSocket/{token}")
public class WebSocket {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 在线人数
     */
    public static int onlineNumber = 0;

    private static OauthSevice oauthSevice;


    @Autowired
    WebSocket webSocket;

    @Autowired
    public void setChatService(OauthSevice oauthSevice) {
        WebSocket.oauthSevice = oauthSevice;
    }


    /**
     * 以用户的姓名为key，WebSocket为对象保存起来
     */
    private static Map<String, WebSocket> clients = new ConcurrentHashMap<String, WebSocket>();
    /**
     * 以session为key，用户名保存起来 方便取出用户名
     */
    private static Map<Session, String> sessionMaps = new ConcurrentHashMap<Session, String>();
    /**
     * 会话
     */
    private Session session;
    /**
     * 用户名称
     */
    private String userId;

    /**
     * 建立连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("token") String token, Session session) throws IOException {
        onlineNumber++;
        logger.info("现在来连接的客户id：" + session.getId() + "用户名：" + userId);
        Map maps = oauthSevice.checkToken(token);
        if (null == maps || maps.isEmpty()) {
            return;
        }
        this.userId = (String) maps.get("userId");
        this.session = session;
        //把自己的信息加入到map当中去 this 就是webSocket链接
        clients.put(userId, this);
        sessionMaps.put(session, userId);
        logger.info("有连接关闭！ 当前在线人数" + clients.size());

    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("服务端发生了错误" + error.getMessage());
        //error.printStackTrace();
    }

    /**
     * 连接关闭
     */
    @OnClose
    public void onClose() {
        onlineNumber--;
        clients.remove(userId);
        sessionMaps.remove(session);
        logger.info("有连接关闭！ 当前在线人数" + clients.size());
    }

    /**
     * 收到客户端的消息
     *
     * @param message 消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {
        Message mes = JSON.parseObject(message, Message.class);
        sendMessageTo(mes);
    }


    public void sendMessageTo(Message message) throws IOException, EncodeException {

        synchronized (this.session) {
            WebSocket webSocket = clients.get(message.getTo());
            webSocket.session.getBasicRemote().sendObject(message);
        }

    }

    public void sendMessageAll(Message message) throws IOException {
        for (WebSocket item : clients.values()) {
            item.session.getAsyncRemote().sendObject(message);
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineNumber;
    }

}