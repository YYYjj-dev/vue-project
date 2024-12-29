package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.ReplyDao;
import com.test.pojo.Reply;

import java.util.List;

public class ReplyDaoImpl extends BaseDao implements ReplyDao {
    @Override
    public int addReply(Reply reply) {
        String sql = "insert into reply values(DEFAULT,?,?,?,?)";
        return baseUpdate(sql,reply.getCommentId(),reply.getContent(),reply.getDate(),reply.getUsername());
    }

    @Override
    public List<Reply> showReply() {
        String sql = "select id,comment_id commentId,content,date,username from reply";
        return baseQuery(Reply.class, sql);
    }

    @Override
    public String getUsernameByRid(Integer id) {
        String sql = "select username from reply where id = ?";
        List replyList = baseQuery(Reply.class, sql, id);
        return replyList.isEmpty() ? null : replyList.get(0).toString();
    }

    @Override
    public int deleteReply(Integer id) {
        String sql = "delete from reply where id = ?";
        return baseUpdate(sql, id);
    }

    @Override
    public List<Reply> findReplyByUid(Integer id) {
        String sql = "select id,comment_id commentId,content,date,username from reply where uid = ?";
        return baseQuery(Reply.class, sql, id);
    }
}
