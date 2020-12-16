package com.blog.security.api.service;

import com.blog.common.model.UserLogin;
import com.blog.security.api.dao.UserDao;
import com.blog.security.api.service.iml.UserInfoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jeffr
 */
@Service
public class UserInfoServiceImp implements UserInfoServiceInterface {
    @Autowired
    private UserDao userDao;

    @Override
    public UserLogin getUserInfo(String userId) {
        return userDao.loadUserByUserId(userId);
    }

    @Override
    public UserLogin getUserInfoByPhone(String phone) {
        return userDao.loadUserByPhone(phone);
    }

}
