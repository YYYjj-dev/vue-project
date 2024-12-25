package com.example.dao;

import com.example.entity.Reply;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ReplyDao {


    @Select("select * from reply where comment_id = #{commentId}")
    List<Reply> getReplysByCommentId(long commentId);

}
