package com.springboot.attendsys.mapper;

import com.springboot.attendsys.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into " +
            "t_user(u_name,u_password,u_email,u_regtime,u_regip,u_role) " +
            "values" +
            "(#{uName},#{uPassword},#{uEmail},#{uRegtime},#{uRegip},#{uRole})")
    int addUser(User user);

    @Update("update t_user set u_lastlogtime = #{t},u_lastlogip = #{ip} where u_email = #{email}")
    int updateWhenLog(@Param("email")String email, @Param("t")Timestamp t,@Param("ip")String ip);

    @Update("update t_user set u_name = #{username},u_password = #{password},u_photo = #{photo},u_sex = #{sex} where u_email = #{email}")
    int updateWhenSaveAdmin(@Param("email")String email, @Param("username")String username,@Param("password")String password,@Param("photo")String photo,@Param("sex")String sex);

    @Select("select * from t_user where u_email = #{email}")
    User getByEmail(@Param("email")String email);

    @Select("select * from t_user where u_id = #{id}")
    User getById(@Param("id")long id);

    @Select("select * from t_user where u_role != 'admin'order by u_id desc limit #{limits},#{page}")
    List<User> getAllUser(@Param("limits")int limits, @Param("page") int page);

    @Select("select count(u_id) from t_user where u_role != 'admin'")
    int countAllUser();

    @Delete("delete from t_user where u_id = #{uid}")
    int deleteUserById(@Param("uid") int uid);
}
