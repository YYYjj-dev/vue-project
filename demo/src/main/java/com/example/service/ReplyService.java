package com.example.service;

import com.example.dao.ReplyDao;
import com.example.entity.Reply;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReplyService {

    @Resource
    private ReplyDao replyDao;

    public List<Reply> getReplysByCommentId(long commentId) {
        return replyDao.getReplysByCommentId(commentId);
    }
}
