package com.springboot.attendsys.controller;

import com.fasterxml.jackson.core.sym.NameN;
import com.springboot.attendsys.model.Comment;
import com.springboot.attendsys.model.Question;
import com.springboot.attendsys.model.User;
import com.springboot.attendsys.service.CommentService;
import com.springboot.attendsys.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;


@Controller
public class PostController {
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;

    /*
     * Create a post
     */
    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public String createPost(User user, @ModelAttribute("question") Question question) {
        question.setuId(user.getuId());
        question.setqTime(new Timestamp(System.currentTimeMillis()));
        questionService.createQuestion(question);
        return "redirect:/posts";
    }

    /*
     * Delete a post
     */
    @PostMapping("/delposts/{qid}")
    public String deletePost(@PathVariable("qid") int qid, User user) {
        //管理员和提出问题的用户可以删除该问题
        if (user.getuRole().equals("admin") || questionService.findQuestion(qid).getuId() == user.getuId()) {
            questionService.deleteQusetion(qid);
            return "redirect:/posts";
        }
        return "redirect:/posts";
    }

    /*
     * Update a post
     */
    @RequestMapping("/updposts")
    public String updatePost(@ModelAttribute("question") Question question, User user) {
        //管理员和提出问题的用户可以对问题进行修改
        if (user.getuRole().equals("admin") || question.getuId() == user.getuId()) {
            questionService.updateQuestion(question);
            return "redirect:/posts";
        }
        return "redirect:/posts";
    }

    /*
     * List all posts
     */
    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public String listAllPosts(Model model) {
        List<Question> questions = questionService.findAllQuestion();
        model.addAttribute("questions", questions);
        model.addAttribute("question", new Question());
        return "questionlist";
    }

    /*
     * Display post details
     */
    @RequestMapping(value = "/posts/{qid}", method = RequestMethod.GET)
    public String displayPostDetails(Model model, @PathVariable("qid") int qid,User user) {
        Question question = questionService.findQuestion(qid);
        model.addAttribute("question", question);
        List<Comment> comments = commentService.listComment(qid);
        model.addAttribute("comments", comments);
        model.addAttribute("user", user);
        return "questiondetail";
    }

    @PostMapping("/comment")
    public String post(Comment comment,Model model,User user) {
        if (comment.getParentComment()!=null) {
            comment.setParentMid(comment.getParentComment().getmId());
        }
        commentService.saveComment(comment);
        List<Comment> comments = commentService.listComment(comment.getqId());
        model.addAttribute("comments", comments);
        model.addAttribute("user", user);
        return "questiondetail::commentList";
    }
}
