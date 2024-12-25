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

    @Override
    public List<Cases> findCasesByType(String type) {
        String sql = "select title,id from cases where grouptype=?";
        List<Cases> casesList = baseQuery(Cases.class, sql, type);
        return casesList.isEmpty() ? null : casesList;
    }

    public List<Cases> findCasesByTitle(String title) {
        String sql = "select title,id from cases where title like ?";
        return baseQuery(Cases.class, sql, title);
    }
    // 增
    public int addCases(Cases cases) {
        String sql = "insert into cases (title,content,admin_id,date,grouptype,imgpath) values (?, ?, ?)";
        Object[] args = {cases.getTitle(), cases.getContent(), cases.getAdminId(),cases.getDate(),cases.getGrouptype(),cases.getImgpath()};
        return baseUpdate(sql, args);
    }

    // 删
    public int deleteCases(Integer id) {
        String sql = "delete from cases where id = ?";
        return baseUpdate(sql, id);
    }

    // 改
    public int updateCases(Cases cases) {
        String sql = "update cases set title = ?, content = ?, date = ?,grouptype = ? where id = ?";
        Object[] args = {cases.getTitle(), cases.getContent(), cases.getDate(),cases.getGrouptype(), cases.getId()};
        return baseUpdate(sql, args);
    }

}