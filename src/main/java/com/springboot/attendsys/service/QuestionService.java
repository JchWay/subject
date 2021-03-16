package com.springboot.attendsys.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springboot.attendsys.mapper.QuestionMapper;
import com.springboot.attendsys.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService{

    @Autowired
    private QuestionMapper questionMapper;

    public int createQuestion(Question question) {
        return questionMapper.createQuestion(question);
    }

    public int deleteQusetion(int qid) {
        return questionMapper.deleteQuestion(qid);
    }

    public int updateQuestion(Question question) {
        return questionMapper.updateQuestion(question);
    }

    public Question findQuestion(int qid) {
        Question question = questionMapper.findQuestion(qid);
        return question;
    }

    public List<Question> findAllQuestion() {
        List<Question> allQuestion = questionMapper.findAllQuestion();
        // all posts
        return allQuestion;
    }

}