package com.test.dao;

import com.test.pojo.Cases;

import java.util.List;

public interface CasesDao {
    List<Cases> findAllCases();
    Cases findCasesById(Integer id);
}
