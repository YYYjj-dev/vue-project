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

    public int addNewsComment(Integer id,Integer cid ,String content, String date) {
        String sql = "insert into comment (user_id,news_id,content,date) values(?,?,?,?)";
        return baseUpdate(sql, id, cid, content, date);
    }

    public int addShangpinComment(Integer uid, Integer cid,String content, String date) {
        String sql = "insert into comment (user_id,shangpin_id,content,date) values(?,?,?,?)";
        return baseUpdate(sql, uid, cid, content, date);
    }

    @Override
    public int deleteCommentById(Integer commentId) {
        String sql = "delete from comment where id=?";
        return baseUpdate(sql, commentId);
    }
}
