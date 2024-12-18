package com.test.dao;

import com.test.pojo.Comment;
import com.test.pojo.News;

import java.util.List;

public class CommentDao extends BaseDao{
    public List<Comment> findNewsCommentById(Integer commentId) {
        String sql = "select user_id userId,content,date from news where news_id=?";
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
}
