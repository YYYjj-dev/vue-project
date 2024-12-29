package com.test.dao;

import com.test.pojo.Comment;
import com.test.pojo.CommentList;

import java.util.List;

public interface CommentDao {

    int addComment(Integer uid, Integer cid, String content, String commentType, String date);

    int deleteCommentById(Integer commentId);

    List<CommentList> findNewsCommentById(Integer commentId);

    List<CommentList> findShangpinCommentById(Integer commentId);

    List<CommentList> findCasesCommentById(Integer commentId);

    List<Comment> findCommentByUid(Integer id);

    Comment getCommentById(Integer id);
}
