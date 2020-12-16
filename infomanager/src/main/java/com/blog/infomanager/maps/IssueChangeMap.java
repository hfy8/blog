package com.blog.infomanager.maps;

import com.blog.infomanager.pojo.DynicInstance;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * issueChangeMap
 *
 * @author jeffrey
 * @description 动态发布和改动
 * @date created in 15:45 2020/10/22
 * @modifyBy
 */
@Mapper
@Repository
public interface IssueChangeMap {
    /**
     * @description 发布动态信息
     * @param
     * @return
     */
    @Insert("INSERT INTO dynicIssue (d_id,u_id,content,title,images,u_name,u_icon) VALUES (#{item.dId},#{item.uId},#{item.content},#{item.title},#{item.images},#{item.uName},#{item.uIcon})")
    void publicIssue(@Param("item") DynicInstance item);
    /**
     * @description 点赞动态信息
     * @param
     * @return
     */
    @Update("update dynicIssue set thumb_up= thumb_up+1 where d_id=#{instanceId}")
    void thumdIssue(@Param("instanceId") Integer instanceId);
    /**
     * @description 评论动态信息
     * @param
     * @return
     */
    @Update("update dynicIssue set discussNum=discussNum+1 where d_id=#{instanceId}")
    void commentIssue(@Param("instanceId") Integer instanceId);

}
