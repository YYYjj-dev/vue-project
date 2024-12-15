package com.test.service;

import com.test.pojo.News;

import java.util.List;

public interface InfoService {
    List<News> findAllNews();

    News findNewsById(Integer id);
}
