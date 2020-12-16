package com.blog.infomanager.pojo;

import lombok.Data;

import java.sql.Timestamp;

/**
 * dynicInstance
 *
 * @author jeffrey
 * @description
 * @date created in 16:06 2020/10/22
 * @modifyBy
 */
@Data
public class DynicInstance {
    private int dId;
    private String uId;
    private String uName;
    private String content;
    private String title;
    private Timestamp createTime;
    private int thumbUp;
    private int discussNum;
    private String images;
    private String uIcon;
}
