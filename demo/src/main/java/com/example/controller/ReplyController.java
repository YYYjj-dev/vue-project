package com.example.controller;


import com.example.common.Result;
import com.example.entity.Comment;
import com.example.entity.New;
import com.example.entity.Reply;
import com.example.service.ReplyService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/reply")
public class ReplyController {

    @Resource
    private ReplyService replyService;

    @GetMapping("/comment/{commentId}")
    public Result getReplysByCommentId(@PathVariable("commentId") long commentId) {
        List<Reply> replies = replyService.getReplysByCommentId(commentId);
        return Result.success(replies);
    }
}
