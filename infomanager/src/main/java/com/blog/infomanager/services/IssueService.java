package com.blog.infomanager.services;

import com.blog.common.model.PageEntity;
import com.blog.common.model.ReturnMessage;
import com.blog.infomanager.maps.IssueChangeMap;
import com.blog.infomanager.maps.IssueGetMap;
import com.blog.infomanager.pojo.DynicInstance;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * issueService
 *
 * @author jeffrey
 * @description
 * @date created in 15:40 2020/10/22
 * @modifyBy
 */
@Service
@Slf4j
public class IssueService {
    @Autowired
    IssueGetMap issueGetMap;
    @Autowired
    IssueChangeMap issueChangeMap;

    public ReturnMessage getIssues(String uid,Integer page,Integer size) {
        PageHelper.startPage(page, size);
        List<DynicInstance> issues = issueGetMap.getIssue(uid);
        return getReturnMessage(issues);
    }

    ;

    public ReturnMessage getGlobalIssues(Integer page,Integer size) {
        PageHelper.startPage(page, size);
        List<DynicInstance> issues = issueGetMap.getGlobalIssue();
        return getReturnMessage(issues);
    }

    public ReturnMessage createIssue(DynicInstance dynicInstance) {
        issueChangeMap.publicIssue(dynicInstance);
        ReturnMessage message = new ReturnMessage();
        return message;
    }

    public ReturnMessage thumbUpIssue(Integer instanceId) {
        issueChangeMap.thumdIssue(instanceId);
        ReturnMessage message = new ReturnMessage();
        return message;
    }
    /**
     *  自定义方法，获取消息对象
     * @param
     * @return
     */
    private ReturnMessage getReturnMessage(List<DynicInstance> issues) {
        ReturnMessage message = new ReturnMessage<PageEntity>();
        PageInfo<DynicInstance> pageAttachments = new PageInfo<>(issues);
        PageEntity<DynicInstance> content=new PageEntity<>(pageAttachments.getTotal(),pageAttachments.getList());
        message.setContent(content);
        return message;
    }
}
