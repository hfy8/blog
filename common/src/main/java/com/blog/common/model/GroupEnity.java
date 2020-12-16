package com.blog.common.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class GroupEnity implements Serializable {
    
    private String groupId;
    private String groupName;

}
