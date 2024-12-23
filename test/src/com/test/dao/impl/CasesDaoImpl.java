package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.CasesDao;
import com.test.pojo.Cases;

import java.util.List;

public class CasesDaoImpl extends BaseDao implements CasesDao {
    public List<Cases> findAllCases() {
        String sql = "select title,id from cases";
        return baseQuery(Cases.class, sql);
    }

    public Cases findCasesById(Integer id) {
        String sql = "select title,content,date from news where id=?";
        List<Cases> casesList = baseQuery(Cases.class, sql, id);
        return casesList.isEmpty() ? null : casesList.get(0);
    }

    public List<Cases> findByTitle(String title) {
        String sql = "select title,id from cases where title like?";
        return baseQuery(Cases.class, sql, title);
    }
    // 实现增加 Cases 的方法
    public int addCases(Cases cases) {
        String sql = "insert into cases (title, content, date) values (?, ?, ?)";
        Object[] args = {cases.getTitle(), cases.getContent(), cases.getDate()};
        return baseUpdate(sql, args);
    }

    // 实现删除 Cases 的方法
    public int deleteCases(Integer id) {
        String sql = "delete from cases where id = ?";
        return baseUpdate(sql, id);
    }

    // 实现修改 Cases 的方法
    public int updateCases(Cases cases) {
        String sql = "update cases set title = ?, content = ?, date = ? where id = ?";
        Object[] args = {cases.getTitle(), cases.getContent(), cases.getDate(), cases.getId()};
        return baseUpdate(sql, args);
    }

    public List<Cases> findCasesByName(String name) {
        String sql = "select * from cases where title like ?";
        Object[] args = {"%" + name + "%"};
        return baseQuery(Cases.class, sql, args);
    }
}
