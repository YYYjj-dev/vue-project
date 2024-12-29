package com.test.dao;

import com.test.pojo.Reply;

import java.util.List;

public interface ReplyDao {
    int addReply(Reply reply);

    List<Reply> showReply();

    String getUsernameByRid(Integer id);

    int deleteReply(Integer id);

    List<Reply> findReplyByUid(Integer id);
}
