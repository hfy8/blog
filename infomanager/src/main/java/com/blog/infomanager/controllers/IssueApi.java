package com.blog.infomanager.controllers;

import com.blog.common.model.LoginInfo;
import com.blog.common.model.ReturnMessage;
import com.blog.infomanager.pojo.Comment;
import com.blog.infomanager.pojo.DynicInstance;
import com.blog.infomanager.services.CommentService;
import com.blog.infomanager.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * issueApi
 *
 * @author jeffrey
 * @description APP个人问题发布模块
 * @date created in 10:54 2020/10/22
 * @modifyBy
 */
@RestController
@RequestMapping("issue/")
public class IssueApi {

    @Autowired
    IssueService issueService;
    @Autowired
    CommentService commentService;

    /**
     * @param
     * @return
     * @description 创建动态信息
     */
    @PostMapping("create")
    @ResponseBody
    public ReturnMessage createIssue(@RequestBody DynicInstance instance) {
        // 获取用户信息
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginInfo user = (LoginInfo) auth.getPrincipal();
        instance.setUName(user.getUserName());
        instance.setUId(user.getUserId());
        instance.setUIcon(user.getUserIcon());
        return issueService.createIssue(instance);

    }

    /**
     * @param
     * @return
     * @description 获取动态信息
     */
    @GetMapping("list")
    @ResponseBody
    public ReturnMessage getIssues(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                   @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        LoginInfo user = (LoginInfo) auth.getPrincipal();
        return issueService.getIssues(user.getUserId(), page, size);
    }

    /**
     * @param
     * @return
     * @description 获取全局动态信息
     */
    @GetMapping("lists")
    @ResponseBody
    public ReturnMessage getGlobalIssues(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
        return issueService.getGlobalIssues(page, size);
    }

    /**
     * @param
     * @return
     * @description 点赞动态
     */
    @PostMapping("thumbup/{instanceId}")
    @ResponseBody
    public ReturnMessage thumbUpIssue(@PathVariable("instanceId") Integer instanceId) {
        return issueService.thumbUpIssue(instanceId);
    }

    /**
     * @param
     * @return
     * @description 评论动态
     */
    @PostMapping("comment")
    @ResponseBody
    public ReturnMessage commentIssue(@RequestBody Comment comment) {
        return commentService.createComment(comment);
    }

}
