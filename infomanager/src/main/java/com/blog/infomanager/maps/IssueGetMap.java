package com.blog.infomanager.maps;

import com.blog.infomanager.pojo.DynicInstance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * issueMap
 *
 * @author jeffrey
 * @description 动态信息查询
 * @date created in 15:41 2020/10/22
 * @modifyBy
 */
@Mapper
@Repository
public interface IssueGetMap {


    /**
     * @description 取数据库对应用户的动态信息
     * @param uid
     * @return Void
     */
    @Select("SELECT i.d_id,i.u_id,i.content,i.title,i.createtime,i.thumb_up,i.images,i.u_name,i.discussNum ,i.u_icon FROM dynicIssue i where i.u_id=#{uid} order by i.createtime desc")
    @ResultType(DynicInstance.class)
    List<DynicInstance> getIssue(String uid);

    /**
     * @description 取数据库全局动态信息
     * @param
     * @return
     */
    @Select("SELECT i.d_id,i.u_id,i.content,i.title,i.createtime,i.thumb_up,i.images,i.u_name,i.discussNum ,i.u_icon FROM dynicIssue i order by i.createtime desc")
    @ResultType(DynicInstance.class)
    List<DynicInstance> getGlobalIssue();
}
