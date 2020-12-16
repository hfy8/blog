package com.blog.infomanager.services;

import com.blog.common.model.PageEntity;
import com.blog.common.model.ReturnMessage;
import com.blog.infomanager.maps.CommentMap;
import com.blog.infomanager.maps.IssueChangeMap;
import com.blog.infomanager.pojo.Comment;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * commentService
 *
 * @author jeffrey
 * @description
 * @date created in 15:40 2020/10/22
 * @modifyBy
 */
@Service
public class CommentService {
    @Autowired
    CommentMap commentMap;
    @Autowired
    IssueChangeMap issueChangeMap;
    public ReturnMessage<List<Comment>> getComments(Integer instanceId,Integer page,Integer size) {
        PageHelper.startPage(page, size);
        List<Comment> comments = commentMap.getComments(instanceId);
        ReturnMessage message = new ReturnMessage<PageEntity>();
        PageInfo<Comment> pageAttachments = new PageInfo<>(comments);
        PageEntity<Comment> content=new PageEntity<>(pageAttachments.getTotal(),pageAttachments.getList());
        message.setContent(content);
        return message;
    }

    @Transactional
    public ReturnMessage createComment(Comment comment) {
        commentMap.createComment(comment);
        issueChangeMap.commentIssue(comment.getDId());
        return  new ReturnMessage();
    }
}
