/*
package com.springboot.attendsys.controller;

import com.springboot.attendsys.model.Comment;
import com.springboot.attendsys.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;


    @RequestMapping("/comment")
    public String comments(Model model) {
        List<Comment> comments = commentService.listComment();
        model.addAttribute("comments", comments);
        return "questiondetail :: commentList";
    }

    @PostMapping("/comment")
    public String post(Comment comment) {
        //设置头像
        if (comment.getParentComment().getId() != null) {
            comment.setParentCommentId(comment.getParentComment().getId());
        }
        commentService.saveComment(comment);
        return "redirect:/comment";
    }
}*/
