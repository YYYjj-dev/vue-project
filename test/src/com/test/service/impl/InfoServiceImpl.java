package com.test.service.impl;

import com.test.dao.CasesDao;
import com.test.dao.NewsDao;
import com.test.pojo.Cases;
import com.test.pojo.News;
import com.test.service.InfoService;

import java.util.Collections;
import java.util.List;

public class InfoServiceImpl implements InfoService {
    private NewsDao newsDao = new NewsDao();
    private CasesDao casesDao = new CasesDao();
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
}
