package com.test.service;

import com.test.pojo.*;

import java.util.List;
import java.util.Map;

public interface InfoService {
    List<News> findAllNews();

    News findNewsById(Integer id);

    int addNews(News news);

    int DeleteNews(Integer id);

    List<Cases> findAllCases();

    Cases findCasesById(Integer id);

    List<Regulation> findAllAllRegular();

    Regulation findRegulationById(Integer id);

    List<CommentList> findNewsCommentById(Integer commentId);

    int addComment(Integer id,Integer cid, String content, String commentType, String date);

    List<CommentList> findShangpinCommentById(Integer commentId);


    List<Cases> findCasesByType(String type);

    int addRegular(Regulation regulation);

    int updateRegular(Regulation regulation);

    int deleteRegular(Integer id);

    int updateNews(News news);

    List<News> findNewsByTitle(String title);

    int addCases(Cases cases);

    int updateCases(Cases cases);

    int deleteCases(Integer id);

    List<CommentList> findCasesCommentById(Integer commentId);

    int deleteComment(Integer id);

    List<Comment> findCommentByUid(Integer id);

    List<News> findNewsByType(String type);

    int addFeedback(Feedback feedback);

    List<Feedback> findFeedbackById(Integer id);

    int deleteFeedback(Integer id);

    int addCarousel(Image img);

    int deleteCarousel(Integer id);

    List<News> findNews(Map<String, Object> queryParams);

    List<Cases> findCases(Map<String, Object> queryParams);

    List<Regulation> findRegularByTitle(String title);

    Comment getCommentById(Integer id);

    int addReply(Reply reply);

    List<Reply> showReply();

    String getUsernameByRid(Integer id);

    int deleteReply(Integer id);

    List<Reply> findReplyByUid(Integer id);

    int addShangpinComment(Comment comment);

    int addNewsComment(Comment comment);

    int addCasesComment(Comment comment);
}
