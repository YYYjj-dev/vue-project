package com.test.service;

import com.test.pojo.Cases;
import com.test.pojo.Comment;
import com.test.pojo.News;
import com.test.pojo.Regulation;

import java.util.List;

public interface InfoService {
    List<News> findAllNews();

    News findNewsById(Integer id);

    int addNews(String title, String content, String date, Integer adminId);

    int DeleteNews(Integer id);

    List<Cases> findAllCases();

    Cases findCasesById(Integer id);

    List<Regulation> findAllAllRegular();

    Regulation findRegulationById(Integer id);

    List<Comment> findNewsCommentById(Integer commentId);

    int addComment(Integer id,Integer cid, String content, String commentType, String date);

    List<Comment> findShangpinCommentById(Integer commentId);


    List<Cases> findCasesByType(String type);

    int addRegular(Regulation regulation);

    int updateRegular(Regulation regulation);

    int deleteRegular(Integer id);
}
