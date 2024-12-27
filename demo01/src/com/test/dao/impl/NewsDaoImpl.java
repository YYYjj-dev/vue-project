package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.NewsDao;
import com.test.pojo.News;

import java.util.List;

public class NewsDaoImpl extends BaseDao implements NewsDao {
    public List<News> findAllNews() {
        String sql = "select *  from news";
        return baseQuery(News.class, sql);
    }

    public News findNewsById(Integer id) {
        String sql = "select * from news where id=?";
        List<News> newsList = baseQuery(News.class, sql, id);
        return newsList.isEmpty() ? null : newsList.get(0);
    }

    public List<News> findNewsByTitle(String title) {
        String sql = "select * from news where title like ?";
        return baseQuery(News.class, sql, title);
    }

    @Override
    public int DeleteNews(Integer id) {
        String sql = "delete from news where id=?";
        return baseUpdate(sql, id);
    }

    @Override
    public int addNews(News news) {
        String sql = "insert into news(title,content,date,type) values(?,?,?,?)";
        return baseUpdate(sql, news.getTitle(), news.getContent(), news.getDate(),news.getType());
    }

    @Override
    public int updateNews(News news) {
        String sql = "update news set title=?,content=?,date=? where id=?";
        return baseUpdate(sql, news.getTitle(), news.getContent(), news.getDate(),news.getId());
    }
}
