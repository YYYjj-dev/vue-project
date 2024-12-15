package com.test.dao;

import com.test.pojo.News;
import java.util.List;

public class NewsDao extends BaseDao{
    public List<News> findAllNews() {
        String sql = "select title,id from news";
        List<News> itemList = baseQuery(News.class, sql);
        return itemList;
    }
}
