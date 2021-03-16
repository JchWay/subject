package com.springboot.attendsys.mapper;

import com.springboot.attendsys.model.Course;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface CourseMapper {
    @Insert("insert into " +
            "t_course(c_name,c_cover,c_creater,c_begintime,c_endtime) " +
            "values" +
            "(#{coursename},#{cover},#{creater},#{bt},#{et})")
    int createCourse(@Param("creater")String creater, @Param("coursename")String coursename, @Param("cover")String cover,
                     @Param("bt")Timestamp bt, @Param("et")Timestamp et);

    @Insert("insert into " +
            "t_selected(u_id,c_id) " +
            "values" +
            "(#{uid},#{cid})")
    int selectById(@Param("cid")int cid, @Param("uid")int uid);

    @Select("select * from t_course where c_creater = #{creater}")
    List<Course> getCourseByCreater(@Param("creater")String email);

    @Select("select * from t_course where c_id in (select c_id from t_selected where u_id = #{uid}) order by c_id desc limit #{limits},#{page}")
    List<Course> getCourseBySelected(@Param("uid")int uid, @Param("limits")int limits, @Param("page") int page);

    @Select("select * from t_course order by c_id desc limit #{limits},#{page}")
    List<Course> getAllCourse(@Param("limits")int limits, @Param("page") int page);

    @Select("select * from t_course where c_id = #{cid}")
    Course getCourseById(@Param("cid")int cid);

    @Update("update t_course set c_lon = #{lon},c_la = #{la},c_atime = #{atime} where c_id = #{cid} ")
    int pubAttend(@Param("lon")double lon, @Param("la") double la, @Param("cid")int cid,@Param("atime")Timestamp atime);

    @Select("select count(c_id) from t_course")
    int countAllCourse();

    @Select("select count(e_id) from t_selected where u_id = #{uid}")
    int countAllMyCourse(@Param("uid")int uid);

    @Delete("delete from t_course where c_id = #{cid}")
    int deleteCourseById(@Param("cid") int cid);

    @Delete("delete from t_selected where c_id = #{cid} and u_id = #{uid}")
    int deleteselected(@Param("cid") int cid, @Param("uid") int uid);
}
