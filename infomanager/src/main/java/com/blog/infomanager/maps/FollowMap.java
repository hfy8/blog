package com.blog.infomanager.maps;

import com.blog.infomanager.pojo.Follow;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jeffr
 */
@Mapper
@Repository
public interface FollowMap {
    /**
     * 插入关注关系
     * @param follow
     * @return
     */
    @Insert("insert into follow(u_id,f_uid,f_uname,u_name) values(#{follow.uId},#{follow.fUid},#{follow.fUname},#{follow.uName})")
    void followUser(@Param("follow") Follow follow);


    /**
     * 更新关注
     * @param follow
     * @return
     */
    @Insert("update follow set status=#{follow.status} where u_id =#{follow.uId} and f_uid =#{follow.fUid}")
    void updateFollow(@Param("follow") Follow follow);

    /**
     * 获取关注关系
     * @param uId
     * @return
     */
    @Select("select u_id,f_uid,f_uname,u_name from follow where u_id=#{u_id} and status=1 ")
    @ResultType(Follow.class)
    List<Follow> getFollows(@Param("u_id") String uId);

    /**
     * 获取单独关注关系
     * @param follow
     * @return
     */
    @Select("select u_id,f_uid,f_uname,u_name from follow where u_id=#{follow.uId} and f_uid=#{follow.fUid} ")
    @ResultType(Follow.class)
    Follow getFollow(@Param("follow") Follow follow);

}
