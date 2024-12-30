package com.test.service.impl;


import com.test.dao.*;
import com.test.dao.impl.*;
import com.test.pojo.*;
import com.test.service.InfoService;

import java.util.List;
import java.util.Map;

public class InfoServiceImpl implements InfoService {
    private NewsDao newsDao = new NewsDaoImpl();
    private CasesDao casesDao = new CasesDaoImpl();
    private CommentDao commentDao = new CommentDaoImpl();
    private RegulationDao regulationDao = new RegulationDaoImpl();
    private FeedbackDao feedbackDao = new FeedbackImpl();
    private ImageDao imageDao = new ImageDaoImpl();
    private ReplyDao replyDao = new ReplyDaoImpl();

    @Override
    public int addNews(News news) {
        return newsDao.addNews(news);
    }

    @Override
    public List<News> findAllNews() {
        return newsDao.findAllNews();
    }

    @Override
    public News findNewsById(Integer id) {
        return newsDao.findNewsById(id);
    }

    @Override
    public List<Cases> findAllCases() {
        return casesDao.findAllCases();
    }

    @Override
    public List<News> findNewsByType(String type) {
        return newsDao.findNewsByType(type);
    }

    @Override
    public int addFeedback(Feedback feedback) {
        return feedbackDao.addFeedback(feedback);
    }

    @Override
    public List<Feedback> findFeedbackById(Integer id) {
        return feedbackDao.findFeedbackById(id);
    }

    @Override
    public int deleteFeedback(Integer id) {
        return feedbackDao.deleteFeedback(id);
    }

    @Override
    public int addCarousel(Image image) {
        return imageDao.addCarousel(image);
    }

    @Override
    public int deleteCarousel(Integer id) {
        return imageDao.deleteCarousel(id);
    }

    @Override
    public List<News> findNews(Map<String, Object> queryParams) {
        return newsDao.findNews(queryParams);
    }

    @Override
    public List<Cases> findCases(Map<String, Object> queryParams) {
        return casesDao.findCases(queryParams);
    }

    @Override
    public List<Regulation> findRegularByTitle(String title) {
        return regulationDao.findRegularByTitle(title);
    }

    @Override
    public Comment getCommentById(Integer id) {
        return commentDao.getCommentById(id);
    }

    @Override
    public int addReply(Reply reply) {
        return replyDao.addReply(reply);
    }

    @Override
    public List<Reply> showReply() {
        return replyDao.showReply();
    }

    @Override
    public String getUsernameByRid(Integer id) {
        return replyDao.getUsernameByRid(id);
    }

    @Override
    public int deleteReply(Integer id) {
        return replyDao.deleteReply(id);
    }

    @Override
    public List<Reply> findReplyByUid(Integer id) {
        return replyDao.findReplyByUid(id);
    }

    @Override
    public int addShangpinComment(Comment comment) {
        return commentDao.addShangpinComment(comment);
    }

    @Override
    public int addNewsComment(Comment comment) {
        return commentDao.addNewsComment(comment);
    }

    @Override
    public int addCasesComment(Comment comment) {
        return commentDao.addCasesComment(comment);
    }

    @Override
    public List<Feedback> findFeedbackByType(String type) {
        return feedbackDao.findFeedbackByType(type);
    }

    @Override
    public List<Feedback> findAllFeedback() {
        return feedbackDao.findAllFeedback();
    }

    @Override
    public Cases findCasesById(Integer id) {
        return casesDao.findCasesById(id);
    }

    @Override
    public List<Regulation> findAllAllRegular() {
        return regulationDao.findAllAllRegular();
    }

    @Override
    public Regulation findRegulationById(Integer id) {
        return regulationDao.findRegulationById(id);
    }

    @Override
    public int addRegular(Regulation regulation) {
        return regulationDao.addRegular(regulation);
    }

    @Override
    public int updateRegular(Regulation regulation) {
        return regulationDao.updateRegular(regulation);
    }

    @Override
    public int deleteRegular(Integer id) {
        return regulationDao.deleteRegular(id);
    }

    @Override
    public int updateNews(News news) {
        return newsDao.updateNews(news);
    }

    @Override
    public List<News> findNewsByTitle(String title) {
        return newsDao.findNewsByTitle(title);
    }

    @Override
    public List<CommentList> findNewsCommentById(Integer commentId) {
        return commentDao.findNewsCommentById(commentId);
    }
    @Override
    public int DeleteNews(Integer id) {
        return newsDao.DeleteNews(id);
    }


    public List<CommentList> findShangpinCommentById(Integer commentId) {
        return commentDao.findShangpinCommentById(commentId);
   }

    @Override
    public List<Cases> findCasesByType(String type) {
        return casesDao.findCasesByType(type);
    }

    @Override
    public int addCases(Cases cases) {
        return casesDao.addCases(cases);
    }

    @Override
    public int updateCases(Cases cases) {
        return casesDao.updateCases(cases);
    }

    @Override
    public int deleteCases(Integer id) {
        return casesDao.deleteCases(id);
    }

    @Override
    public List<CommentList> findCasesCommentById(Integer commentId) {
        return commentDao.findCasesCommentById(commentId);
    }

    @Override
    public int deleteComment(Integer id) {
        return commentDao.deleteCommentById(id);
    }

    @Override
    public List<Comment> findCommentByUid(Integer id) {
        return commentDao.findCommentByUid(id);
    }



    @Override
    public int addComment(Integer uid, Integer cid, String content, String commentType, String date) {
        return commentDao.addComment(uid,cid,content,commentType,date);
    }
}
