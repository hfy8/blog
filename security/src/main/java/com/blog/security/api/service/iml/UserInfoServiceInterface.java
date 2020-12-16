package com.blog.security.api.service.iml;

import com.blog.common.model.UserLogin;

/**
 * @author jeffr
 */
public interface UserInfoServiceInterface {

    UserLogin getUserInfo(String userId);


    UserLogin getUserInfoByPhone(String phone);

}
