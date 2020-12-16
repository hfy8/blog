package com.blog.security.api.controller;

import com.blog.common.model.LoginInfo;
import com.blog.common.model.ReturnMessage;
import com.blog.common.model.UserLogin;
import com.blog.security.api.service.iml.UserInfoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user/center/")
public class UserInfoController {
    @Autowired
    UserInfoServiceInterface userInfoService;

    @GetMapping("getUserInfo")
    @ResponseBody
    public ReturnMessage getUserInfo() throws Exception {
        try {
            LoginInfo user = (LoginInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            UserLogin userLogin = userInfoService.getUserInfo(user.getUserId());
            return new ReturnMessage("ok", userLogin, "","");

        } catch (Exception e) {
            throw e;
        }
    }


}
