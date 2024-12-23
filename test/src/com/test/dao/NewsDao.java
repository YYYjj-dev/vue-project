package com.test.dao;

import com.test.pojo.News;

import java.util.List;

public interface NewsDao {
    List<News> findAllNews();

    News findNewsById(Integer id);
}
