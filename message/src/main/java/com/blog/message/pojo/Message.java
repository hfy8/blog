package com.blog.message.pojo;

import lombok.Data;

/**
 * Message
 *
 * @author jeffrey
 * @description
 * @date created in 14:12 2020/12/17
 * @modifyBy
 */
@Data
public class Message {
    private String from;
    private String to;
    private String Content;
}
