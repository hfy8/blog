package com.blog.common.model;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName Account
 * @Date 2019/4/11 16:56
 * @Auther bianjie
 **/
@Data
public class UserLogin implements Serializable {
    private String userId;
    private String account;
    private String userName;
    private String phone;
    private String password;
    private String icon;
    private String email;
    private String showName;
    private Timestamp registerTime;
    private List<RoleEnity> roles;

}
