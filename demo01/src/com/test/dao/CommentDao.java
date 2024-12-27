package com.test.dao;

import com.test.pojo.Comment;

import java.util.List;

public interface CommentDao {
    List<Comment> findNewsCommentById(Integer commentId);

    List<Comment> findShangpinCommentById(Integer commentId);

    int addNewsComment(Integer uid, Integer cid, String content, String date);

    int addShangpinComment(Integer uid, Integer cid, String content, String date);

    int deleteCommentById(Integer commentId);
}
