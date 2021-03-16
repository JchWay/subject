package com.springboot.attendsys.mapper;

import com.springboot.attendsys.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Insert("insert into t_comment(q_id,u_id,m_content,m_time,parent_mid) values(#{qId},#{uId},#{mContent},#{mTime},#{parentMid})")
    int saveComment(Comment comment);

    @Select("select * from t_comment where parent_mid = #{childId} order by m_time desc")
    List<Comment> findByReplayId(@Param("childId") int childId);

    @Select("select * from t_comment where parent_mid = #{pid} order by m_time desc")
    List<Comment> findByParentIdNull(@Param("pid")String pid);

    @Select("select * from t_comment where parent_mid = #{id} order by m_time desc")
    List<Comment> findByParentIdNotNull(@Param("id") int id);
}
