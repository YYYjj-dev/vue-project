package com.test.dao;

import com.test.pojo.Comment;

import java.util.List;

public interface CommentDao {

    int addComment(Integer uid, Integer cid, String content, String commentType, String date);

    int deleteCommentById(Integer commentId);

    List<Comment> findNewsCommentById(Integer commentId);

    List<Comment> findShangpinCommentById(Integer commentId);

    List<Comment> findCasesCommentById(Integer commentId);

    List<Comment> findCommentByUid(Integer id);
}
