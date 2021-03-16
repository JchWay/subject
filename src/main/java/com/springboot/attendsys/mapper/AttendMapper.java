package com.springboot.attendsys.mapper;

import com.springboot.attendsys.model.Attendance;
import com.springboot.attendsys.model.Leave;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface AttendMapper {
    @Insert("insert into " +
            "t_leave(u_id,c_id,l_reason) " +
            "values " +
            "(#{uid},#{cid},#{lreason})")
    int publeave(@Param("uid") int uid, @Param("cid") int cid, @Param("lreason") String lreason);

    @Select("select * from t_leave where u_id = #{uid} and c_id = #{cid}")
    Leave getleavebyids(@Param("uid") int uid, @Param("cid") int cid);

    @Select("select * from t_attendance where u_id = #{uid} and c_id = #{cid}")
    Attendance getattendbyids(@Param("uid") int uid, @Param("cid") int cid);

    @Insert("insert into t_attendance(u_id,c_id) values (#{uid},#{cid})")
    int punch(@Param("uid") int uid, @Param("cid") int cid);

    @Select("select * from t_leave where c_id in (select c_id from t_course where c_creater = #{uemail}) order by l_id limit #{limits},#{page}")
    List<Leave> getallleavebyuser(@Param("uemail")String uemail,@Param("limits")int limits, @Param("page") int page);

    @Select("select count(l_id) from t_leave")
    int countallleave();

    @Select("select * from t_leave where l_id = #{lid}")
    Leave getleavebylid(@Param("lid") int lid);

    @Update("update t_leave set l_status = #{status} where l_id = #{lid} ")
    int approveleave(@Param("lid") int lid,@Param("status") String status);
}
