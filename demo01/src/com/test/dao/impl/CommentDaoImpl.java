package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.CommentDao;
import com.test.pojo.Comment;

import java.util.List;

public class CommentDaoImpl extends BaseDao implements CommentDao {
    public List<Comment> findNewsCommentById(Integer commentId) {
        String sql = "select user_id userId,content,date from comment where news_id=?";
        return baseQuery(Comment.class, sql, commentId);
    }
    public List<Comment> findShangpinCommentById(Integer commentId) {
        String sql = "select user_id userId,content,date from comment where shangpin_id=?";
        return baseQuery(Comment.class, sql, commentId);
    }
    @Override
    public List<Comment> findCasesCommentById(Integer commentId) {
        String sql = "select user_id userId,content,date from comment where cases_id=?";
        return baseQuery(Comment.class, sql, commentId);
    }

    @Override
    public int addComment(Integer uid, Integer cid, String content, String commentType, String date) {
        String sql = null;
        if(commentType.equals("news")){
            sql = "insert into comment (user_id,news_id,content,date) values(?,?,?,?)";
        }else if(commentType.equals("shangpin")){
            sql = "insert into comment (user_id,shangpin_id,content,date) values(?,?,?,?)";
        }else if(commentType.equals("cases")){
            sql = "insert into comment (user_id,case_id,content,date) values(?,?,?,?)";
        }
        return baseUpdate(sql, uid, cid, content, date);
    }

    @Override
    public List<Comment> findCommentByUid(Integer id) {
        String sql = "select user_id userId,shangpin_id shangpinId,cases_id caseId,news_id news_Id,content,date from comment where user_id=?";
        return baseQuery(Comment.class, sql, id);
    }

    @Override
    public int deleteCommentById(Integer commentId) {
        String sql = "delete from comment where id=?";
        return baseUpdate(sql, commentId);
    }
}
