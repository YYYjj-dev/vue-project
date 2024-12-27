package com.test.service.impl;


import com.test.dao.CasesDao;
import com.test.dao.CommentDao;
import com.test.dao.NewsDao;
import com.test.dao.RegulationDao;
import com.test.dao.impl.CasesDaoImpl;
import com.test.dao.impl.CommentDaoImpl;
import com.test.dao.impl.NewsDaoImpl;
import com.test.dao.impl.RegulationDaoImpl;
import com.test.pojo.Cases;
import com.test.pojo.Comment;
import com.test.pojo.News;
import com.test.pojo.Regulation;
import com.test.service.InfoService;

import java.util.List;

public class InfoServiceImpl implements InfoService {
    private NewsDao newsDao = new NewsDaoImpl();
    private CasesDao casesDao = new CasesDaoImpl();
    private CommentDao commentDao = new CommentDaoImpl();
    private RegulationDao regulationDao = new RegulationDaoImpl();

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
    public  List<Comment> findNewsCommentById(Integer commentId) {
        return commentDao.findNewsCommentById(commentId);
    }
    @Override
    public int DeleteNews(Integer id) {
        return newsDao.DeleteNews(id);
    }


    public List<Comment> findShangpinCommentById(Integer commentId) {
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
    public List<Comment> findCasesCommentById(Integer commentId) {
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
