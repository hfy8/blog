package com.blog.security.api.dao;


import com.blog.common.model.RoleEnity;
import com.blog.common.model.UserLogin;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Mapper
@Repository
public interface UserDao {
    @Select(" select u_id,u_name,u_phone,u_account,u_pwd,u_icon,u_email,u_sname,register_time from user" +
            " where u_account = #{account}")
    @Results({
            @Result(id = true, column = "u_id", property = "userId"),
            @Result(column = "u_name", property = "userName"),
            @Result(column = "u_phone", property = "phone"),
            @Result(column = "u_account", property = "account"),
            @Result(column = "u_pwd", property = "password"),
            @Result(column = "U_email", property = "email"),
            @Result(column = "U_icon", property = "icon"),
            @Result(column = "u_sname", property = "showName"),
            @Result(column = "register_time", property = "registerTime"),
            @Result(column = "u_id", property = "roles", many = @Many(select = "com.blog.security.api.dao.UserDao.loadUserRoles", fetchType = FetchType.LAZY)),

    })
    UserLogin loadUserByAccount(String account);


    @Select(" select u_id,u_name,u_phone,u_account,u_pwd,u_icon,u_email,u_sname,register_time from user" +
            " where u_phone = #{phone}")
    @Results({
            @Result(id = true, column = "u_id", property = "userId"),
            @Result(column = "u_name", property = "userName"),
            @Result(column = "u_phone", property = "phone"),
            @Result(column = "u_account", property = "account"),
            @Result(column = "u_pwd", property = "password"),
            @Result(column = "U_email", property = "email"),
            @Result(column = "U_icon", property = "icon"),
            @Result(column = "u_sname", property = "showName"),
            @Result(column = "register_time", property = "registerTime"),
            @Result(column = "u_id", property = "roles", many = @Many(select = "com.blog.security.api.dao.UserDao.loadUserRoles", fetchType = FetchType.LAZY)),

    })
    UserLogin loadUserByPhone(String phone);


    @Select(" select u_id,r_name,r_id,createTime from role_map " +
            " where u_id = #{id}")
    @ResultType(RoleEnity.class)
    ArrayList<RoleEnity> loadUserRoles(String u_id);


    @Select(" select u.id as id,username,realname,password,status,email,leader as leaderid from user u" +
            " where u.id = #{userId}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "userName"),
            @Result(column = "realname", property = "realName"),
            @Result(column = "password", property = "password"),
            @Result(column = "email", property = "email"),
            @Result(column = "leaderid", property = "leaderId"),
            @Result(column = "status", property = "status"),
            @Result(column = "id", property = "roles", many = @Many(select = "com.lintes.authserver.api.dao.UserDao.loadUserRoles")),
            @Result(column = "id", property = "groups", many = @Many(select = "com.lintes.authserver.api.dao.UserDao.loadUserGroups")),


    })
    UserLogin loadUserByUserId(String userId);

    @Select(" select id as role_id,name as role_name as role_name,desc from role")
    @ResultType(RoleEnity.class)
    ArrayList<RoleEnity> loadRoles();

    @Select(" select id as role_id,name as role_name from role where name=#{roleName} ")
    @ResultType(RoleEnity.class)
    RoleEnity loadRolesByRoleName(String roleName);

    @Delete("delete from user where id = #{id} ")
    @ResultType(Integer.class)
    void deleteUser(String id);


    @Delete("delete from user_role where user_id = #{id} ")
    @ResultType(Integer.class)
    void deleteUserRole(String id);

    @Delete("delete from user_role where user_id = #{id} and group_id=#{roleId}")
    @ResultType(Integer.class)
    void deleteUserRoleByRoleId(String id, String roleId);

    @Delete("delete from role where id = #{id} ")
    @ResultType(Integer.class)
    void deleteRole(String id);

    @Insert("insert into user(username,password,realname,leader,email) values(#{userName},#{password},#{realName},#{leaderId},#{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    void addUser(UserLogin userLogin);

    @Insert("insert into role(name,desc) values(#{roleName},#{desc})")
    @Options(useGeneratedKeys = true, keyProperty = "roleId", keyColumn = "id")
    void addRole(RoleEnity roleEnity);

    @Insert("insert into user_role(user_id,role_id) values(#{userId}, #{roleId})")
    void addUserRole(String userId, String roleId);



}
