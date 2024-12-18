package com.test.service.impl;

import com.test.dao.CasesDao;
import com.test.dao.CommentDao;
import com.test.dao.NewsDao;
import com.test.dao.RegulationDao;
import com.test.pojo.Cases;
import com.test.pojo.Comment;
import com.test.pojo.News;
import com.test.pojo.Regulation;
import com.test.service.InfoService;

import java.util.List;

public class InfoServiceImpl implements InfoService {
    private NewsDao newsDao = new NewsDao();
    private CasesDao casesDao = new CasesDao();
    private CommentDao commentDao = new CommentDao();

    private RegulationDao regulationDao = new RegulationDao();
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
    public  List<Comment> findNewsCommentById(Integer commentId) {
        return commentDao.findNewsCommentById(commentId);
    }
    //@Override
//    public List<Comment> findShangpinCommentById(Integer commentId) {
//
//    }

    @Override
    public int addComment(Integer uid, Integer cid, String content, String commentType, String date) {
        if(commentType.equals("1")){
            return commentDao.addNewsComment(uid,cid,content,date);
        }else if(commentType.equals("2")){
            return commentDao.addShangpinComment(uid,cid,content,date);
        }else {
            return 0;
        }
    }
}
