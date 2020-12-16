package com.blog.infomanager.services;

import com.blog.common.model.PageEntity;
import com.blog.common.model.ReturnMessage;
import com.blog.infomanager.maps.FollowMap;
import com.blog.infomanager.pojo.Follow;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FollowService
 *
 * @author jeffrey
 * @description
 * @date created in 18:45 2020/12/14
 * @modifyBy
 */
@Service
public class FollowService {

    @Autowired
    FollowMap followMap;

    public ReturnMessage followUser(Follow follow) {
        Follow fl = followMap.getFollow(follow);
        if (fl != null) {
            follow.setStatus(1);
            followMap.updateFollow(follow);
        } else {
            followMap.followUser(follow);
        }
        return new ReturnMessage();
    }

    public ReturnMessage disFollowUser(Follow follow) {
        follow.setStatus(0);
        followMap.updateFollow(follow);
        return new ReturnMessage();
    }

    public ReturnMessage getFollows(String uId,Integer page,Integer size) {
        PageHelper.startPage(page, size);
        List<Follow> follows = followMap.getFollows(uId);
        ReturnMessage message = new ReturnMessage<PageEntity>();
        PageInfo<Follow> pageAttachments = new PageInfo<>(follows);
        PageEntity<Follow> content=new PageEntity<>(pageAttachments.getTotal(),pageAttachments.getList());
        message.setContent(content);
        return message;
    }
}
