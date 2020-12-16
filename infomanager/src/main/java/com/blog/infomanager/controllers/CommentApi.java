package com.blog.infomanager.controllers;

import com.blog.common.model.ReturnMessage;
import com.blog.infomanager.pojo.Comment;
import com.blog.infomanager.services.CommentService;
import com.blog.infomanager.services.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * commentApi
 *
 * @author jeffrey
 * @description
 * @date created in 18:22 2020/11/2
 * @modifyBy
 */
@RestController
@RequestMapping("comment/")
public class CommentApi {
    @Autowired
    IssueService issueService;
    @Autowired
    CommentService commentService;

    /**
     * 查询评论
     *
     * @param
     * @return
     */
    @GetMapping("lists/{instanceId}")
    @ResponseBody
    public ReturnMessage getComments(@PathVariable("instanceId") Integer instanceId,
                                     @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", required = false, defaultValue = "5") Integer size) {
        return commentService.getComments(instanceId, page, size);
    }

    /**
     * 新的评论
     *
     * @param
     * @return
     */
    @PostMapping("create")
    @ResponseBody
    public ReturnMessage createComment(@RequestBody Comment comment) {
        comment.setCId(UUID.randomUUID().toString());
        return commentService.createComment(comment);
    }
}
