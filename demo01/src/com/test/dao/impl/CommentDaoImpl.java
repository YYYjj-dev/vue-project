package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.CommentDao;
import com.test.pojo.Comment;
import com.test.pojo.CommentList;

import java.util.List;

public class CommentDaoImpl extends BaseDao implements CommentDao {
    public List<CommentList> findNewsCommentById(Integer commentId) {
        String sql = "select comment.id,username,user_id userId,content,date,img,type " +
                "from comment,user " +
                "where news_id=? and user_id=user.id";
        return baseQuery(CommentList.class, sql, commentId);
    }
    public List<CommentList> findShangpinCommentById(Integer commentId) {
        String sql = "select comment.id,username,user_id userId,content,date,img,type " +
                "from comment,user " +
                "where shangpin_id=? and user_id=user.id";
        return baseQuery(CommentList.class, sql, commentId);
    }
    @Override
    public List<CommentList> findCasesCommentById(Integer commentId) {
        String sql = "select comment.id,username,user_id userId,content,date,img,type " +
                "from comment,user " +
                "where cases_id=? and user_id=user.id";
        return baseQuery(CommentList.class, sql, commentId);
    }

    @Override
    public int addComment(Integer uid, Integer cid, String content, String commentType, String date) {
        return 0;
    }
    @Override
    public int addShangpinComment(Comment comment) {
       String sql =  sql = "insert into comment (user_id,shangpin_id,content,date) values(?,?,?,?)";
       return baseUpdate(sql, comment.getUserId(), comment.getShangpinId(), comment.getContent(), comment.getDate());
    }

    @Override
    public int addNewsComment(Comment comment) {
        String sql  = "insert into comment (user_id,news_id,content,date) values(?,?,?,?)";
        return baseUpdate(sql, comment.getUserId(), comment.getNewsId(), comment.getContent(), comment.getDate());
    }

    @Override
    public int addCasesComment(Comment comment) {
        String sql = "insert into comment (user_id,case_id,content,date) values(?,?,?,?)";
        return baseUpdate(sql, comment.getUserId(), comment.getCaseId(), comment.getContent(), comment.getDate());
    }

    @Override
    public List<Comment> findCommentByUid(Integer id) {
        String sql = "select user_id userId,shangpin_id shangpinId,cases_id caseId,news_id news_Id,content,date from comment where user_id=?";
        return baseQuery(Comment.class, sql, id);
    }

    @Override
    public Comment getCommentById(Integer id) {
        String sql = "select user_id userId,content,date from comment where id=?";
        List<Comment> commentList = baseQuery(Comment.class, sql, id);
        return !commentList.isEmpty() ? commentList.get(0) : null;
    }

    @Override
    public int deleteCommentById(Integer commentId) {
        String sql = "delete from comment where id=?";
        return baseUpdate(sql, commentId);
    }
}
