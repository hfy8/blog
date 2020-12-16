package com.blog.infomanager.maps;

import com.blog.infomanager.pojo.Comment;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * commentMap
 *
 * @author jeffrey
 * @description
 * @date created in 15:41 2020/10/22
 * @modifyBy
 */
@Mapper
@Repository
public interface CommentMap {
    @Insert("insert into comment (c_id,u_id,tu_id,d_id,content,tc_id,type,u_name" +
            ",u_icon,tu_name) values(#{comment.cId},#{comment.uId},#{comment.tuId},#{comment.dId},#{comment.content},#{comment.tcId},#{comment.type},#{comment.uName},#{comment.uIcon},#{comment.tuName})")
    void createComment(@Param("comment") Comment comment);

    @Select("select c_id,u_id,tu_id,d_id,content,tc_id,createtime as createTime,type,u_name,u_icon,tu_name from comment where d_id=#{dId}")
    @ResultType(Comment.class)
    List<Comment> getComments(@Param("dId") Integer dId);

}
