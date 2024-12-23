package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.NewsDao;
import com.test.pojo.News;

import java.util.List;

public class NewsDaoImpl extends BaseDao implements NewsDao {
    public List<News> findAllNews() {
        String sql = "select title,id,content from news";
        return baseQuery(News.class, sql);
    }

    public News findNewsById(Integer id) {
        String sql = "select title,content,date,comment_id commentId from news where id=?";
        List<News> newsList = baseQuery(News.class, sql, id);
        return newsList.isEmpty() ? null : newsList.get(0);
    }

    public List<News> findNewsByTitle(String title) {
        String sql = "select title,id from news where title like ?";
        return baseQuery(News.class, sql, title);
    }

    @Override
    public int DeleteNews(Integer id) {
        String sql = "delete from news where id=?";
        return baseUpdate(sql, id);
    }

    @Override
    public int addNews(String title, String content, String date, Integer id) {
        String sql = "insert into news(title,content,date,admin_id) values(?,?,?,?)";
        return baseUpdate(sql, title, content, date,id);
    }
}
