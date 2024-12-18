package com.test.dao;

import com.test.pojo.Cases;
import com.test.pojo.News;

import java.util.List;

public class CasesDao extends BaseDao{
    public List<Cases> findAllCases() {
        String sql = "select title,id from cases";
        return baseQuery(Cases.class, sql);
    }

    public Cases findCasesById(Integer id) {
        String sql = "select title,content,date from news where id=?";
        List<Cases> casesList = baseQuery(Cases.class, sql, id);
        return casesList.isEmpty() ? null : casesList.get(0);
    }
}
