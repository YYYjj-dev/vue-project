package com.example.service;


import com.example.dao.CommentDao;
import com.example.entity.Comment;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Resource
    private CommentDao commentDao;

//    public List<Comment> getComment(){
//        return commentDao.getComment();
//    }

    public List<Comment> getCommentsByNewsId(long newsId) {
        return commentDao.getCommentsByNewsId(newsId);
    }
}
