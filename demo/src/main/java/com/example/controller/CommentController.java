package com.example.controller;


import com.example.common.Result;
import com.example.entity.Comment;
import com.example.service.CommentService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

//    @GetMapping
//    public Result getComment(){
//        List<Comment> commentList = commentService.getComment();
//        return Result.success(commentList);
//    }

    @GetMapping("/news/{newsId}")
    public Result getCommentsByNewsId(@PathVariable("newsId") long newsId) {
        List<Comment> comments = commentService.getCommentsByNewsId(newsId);
        return Result.success(comments);
    }

}
