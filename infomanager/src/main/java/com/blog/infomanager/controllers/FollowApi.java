package com.blog.infomanager.controllers;

import com.blog.common.model.LoginInfo;
import com.blog.common.model.ReturnMessage;
import com.blog.infomanager.pojo.Follow;
import com.blog.infomanager.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * FollowApi
 *
 * @author jeffrey
 * @description
 * @date created in 18:58 2020/12/14
 * @modifyBy
 */
@RestController
@RequestMapping("follow/")
public class FollowApi {
    @Autowired
    FollowService followService;


    /**
     * @param
     * @return
     * @description 关注
     */
    @PostMapping("followUser")
    @ResponseBody
    public ReturnMessage followUser(@RequestBody Follow follow) {
        // 获取用户信息
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginInfo user = (LoginInfo) auth.getPrincipal();
        follow.setUId(user.getUserId());
        follow.setUName(user.getUserName());
        return followService.followUser(follow);

    }

    /**
     * @param
     * @return
     * @description 取关
     */
    @PostMapping("disFollowUser")
    @ResponseBody
    public ReturnMessage disFollowUser(@RequestBody Follow follow) {
        // 获取用户信息
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginInfo user = (LoginInfo) auth.getPrincipal();
        follow.setUId(user.getUserId());
        follow.setUName(user.getUserName());
        return followService.disFollowUser(follow);
    }

    /**
     * @param
     * @return
     * @description 获取关注列表
     */
    @GetMapping(value = {"follows", "follows/{u_id}"})
    @ResponseBody
    public ReturnMessage getFollows(@PathVariable(required = false) String u_id,
                                    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                    @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
        // 获取用户信息
        if (null == u_id || u_id.equals("")) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            LoginInfo user = (LoginInfo) auth.getPrincipal();
            return followService.getFollows(user.getUserId(), page, size);
        } else {
            return followService.getFollows(u_id, page, size);

        }
    }
}
