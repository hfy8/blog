package com.blog.security.api.service;


import com.blog.common.model.UserLogin;
import com.blog.security.api.dao.UserDao;
import com.blog.security.api.service.iml.UserServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jeffr
 */
@Service
@Slf4j
public class UserService implements UserServiceInterface {
    @Autowired
    private UserDao userDao;

    @Override
    public UserLogin loadUserByAccount(String account) {
        return userDao.loadUserByAccount(account);
    }

}
