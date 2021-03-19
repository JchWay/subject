package com.springboot.attendsys.service;

import com.springboot.attendsys.mapper.CommentMapper;
import com.springboot.attendsys.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    /**
     * cription: 查询评论
     *
     * @Return: 评论消息
     */
    public List<Comment> listComment(int qid) {
        //查询出父节点
        int pid = -1;
        List<Comment> comments = commentMapper.findByParentIdNull(qid,pid);
        for (Comment comment : comments) {
            int id = comment.getmId();
            int puid = comment.getuId();
            List<Comment> childComments = commentMapper.findByParentIdNotNull(id);
            //查询出子评论
            combineChildren(childComments, puid);
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return comments;
    }


    /**
     * @Description: 查询出子评论
     * @Param: childComments：所有子评论
     * @Param: parentuId：父评论的用户id
     * @Return:
     */
    private void combineChildren(List<Comment> childComments, int parentuId) {
        //判断是否有一级子回复
        if (childComments.size() > 0) {
            //循环找出子评论的id
            for (Comment childComment : childComments) {
                int puid = childComment.getuId();
                childComment.setParentuId(parentuId);
                tempReplys.add(childComment);
                int childId = childComment.getmId();
                //查询二级以及所有子集回复
                recursively(childId, puid);
            }
        }
    }

    /**
     * @Description: 循环迭代找出子集回复
     * @Param: childId：子评论的id
     * @Param: parentuId：子评论的用户id
     * @Return:
     */
    private void recursively(int childId, int parentuId) {
        //根据子一级评论的id找到子二级评论
        List<Comment> replayComments = commentMapper.findByReplayId(childId);

        if (replayComments.size() > 0) {
            for (Comment replayComment : replayComments) {
                int puid = replayComment.getuId();
                replayComment.setParentuId(parentuId);
                int replayId = replayComment.getmId();
                tempReplys.add(replayComment);
                //循环迭代找出子集回复
                recursively(replayId, puid);
            }
        }
    }

    //存储评论信息
    public int saveComment(Comment comment) {
        comment.setmTime(new Timestamp(System.currentTimeMillis()));
        return commentMapper.saveComment(comment);
    }
}