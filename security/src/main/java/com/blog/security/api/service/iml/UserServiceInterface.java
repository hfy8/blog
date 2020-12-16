package com.blog.security.api.service.iml;


import com.blog.common.model.UserLogin;

public interface UserServiceInterface {
    UserLogin loadUserByAccount(String username);
}
