package com.blog.common.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @ClassName roleEnity
 * @Date 2019/8/7 下午 03:51
 * @Auther bianjie
 **/
@Data
public class RoleEnity implements Serializable {
    private String uid;
    private String rname;
    private String rid;
    private Timestamp createTime;
}
