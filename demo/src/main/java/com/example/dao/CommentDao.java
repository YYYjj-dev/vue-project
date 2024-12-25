package com.example.dao;

import com.example.entity.Comment;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommentDao {

//    @Select("select * from comment")
//    List<Comment> getComment();

    @Select("select * from comment where news_id = #{newsId}")
    List<Comment> getCommentsByNewsId(long newsId);
}
