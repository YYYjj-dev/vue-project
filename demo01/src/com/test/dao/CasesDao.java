package com.test.dao;

import com.test.pojo.Cases;
import com.test.pojo.Comment;

import java.util.List;
import java.util.Map;

public interface CasesDao {
//增删查改
    List<Cases> findAllCases();
    Cases findCasesById(Integer id);
    int addCases(Cases cases);
    List<Cases> findCasesByType(String type);
    List<Cases> findCasesByTitle(String type);
    int updateCases(Cases cases);
    int deleteCases(Integer id);

    List<Cases> findCases(Map<String, Object> queryParams);
}
