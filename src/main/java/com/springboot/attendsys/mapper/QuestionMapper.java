package com.springboot.attendsys.mapper;

import com.springboot.attendsys.model.Question;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into t_question(u_id,q_topic,q_content,q_time) values (#{uId},#{qTopic},#{qContent},#{qTime})")
    int createQuestion(Question question);

    @Delete("delete from t_question where q_id = #{qid}")
    int deleteQuestion(@Param("qid") int qid);

    @Update("update t_question set q_topic = #{qTopic},q_content = #{qContent} where q_id = #{qId}")
    int updateQuestion(Question question);

    @Select("select * from t_question where q_id = #{qid}")
    Question findQuestion(@Param("qid") int qid);

    @Select("select * from t_question order by q_time desc")
    List<Question> findAllQuestion();
}
