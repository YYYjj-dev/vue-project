package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.NewsDao;
import com.test.pojo.News;

import java.util.List;

public class NewsDaoImpl extends BaseDao implements NewsDao {
    public List<News> findAllNews() {
        String sql = "select title,id from news";
        return baseQuery(News.class, sql);
    }

    public News findNewsById(Integer id) {
        String sql = "select title,content,date,comment_id commentId from news where id=?";
        List<News> newsList = baseQuery(News.class, sql, id);
        return newsList.isEmpty() ? null : newsList.get(0);
    }
}
