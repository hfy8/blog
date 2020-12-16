package com.blog.infomanager.pojo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * comment
 *
 * @author jeffrey
 * @description
 * @date created in 18:01 2020/10/26
 * @modifyBy
 */
@Data
public class Comment {
    private String cId;
    private String uId;
    private String tuId;
    private Integer dId;
    private String content;
    private String tcId;
    private Timestamp createTime;
    private int type;
    private String uName;
    private String uIcon;
    private String tuName;


}
