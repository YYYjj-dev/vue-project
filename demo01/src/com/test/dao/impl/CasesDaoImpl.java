package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.CasesDao;
import com.test.pojo.Cases;
import com.test.pojo.Comment;

import java.util.List;
import java.util.Map;

public class CasesDaoImpl extends BaseDao implements CasesDao {
    public List<Cases> findAllCases() {
        String sql = "select * from cases";
        return baseQuery(Cases.class, sql);
    }

    //查
    public Cases findCasesById(Integer id) {
        String sql = "select * from cases where id=?";
        List<Cases> casesList = baseQuery(Cases.class, sql, id);
        return casesList.isEmpty() ? null : casesList.get(0);
    }

    @Override
    public List<Cases> findCasesByType(String type) {
        String sql = "select id,title,content,imgpath,grouptype,date from cases where grouptype=?";
        List<Cases> casesList = baseQuery(Cases.class, sql, type);
        return casesList.isEmpty() ? null : casesList;
    }

    public List<Cases> findCasesByTitle(String title) {
        String sql = "select * from cases where title like ?";
        return baseQuery(Cases.class, sql, title);
    }
    // 增
    public int addCases(Cases cases) {
        String sql = "insert into cases values (DEFAULT, ?, ?, ?, ?,?)";
        Object[] args = {cases.getTitle(), cases.getContent(), cases.getImgpath(), cases.getGrouptype(), cases.getDate()};
        return baseUpdate(sql, args);
    }

    // 删
    public int deleteCases(Integer id) {
        String sql = "delete from cases where id = ?";
        return baseUpdate(sql, id);
    }

    @Override
    public List<Cases> findCases(Map<String, Object> queryParams) {
        String sql = buildQuery(queryParams,"*","cases");
        return baseQuery(Cases.class, sql);
    }


    // 改
    public int updateCases(Cases cases) {
        String sql = "update cases set title = ?, content = ?,imgpath = ?,grouptype = ?, date = ? where id = ?";
        Object[] args = {cases.getTitle(), cases.getContent(),cases.getImgpath(),cases.getGrouptype(), cases.getDate(), cases.getId()};
        return baseUpdate(sql, args);
    }

}
