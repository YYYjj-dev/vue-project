package com.test.service.impl;

import com.test.dao.NewsDao;
import com.test.pojo.News;
import com.test.service.InfoService;

import java.util.Collections;
import java.util.List;

public class InfoServiceImpl implements InfoService {
    private NewsDao newsDao = new NewsDao();
    @Override
    public List<News> findAllNews() {
        return newsDao.findAllNews();
    }
}
