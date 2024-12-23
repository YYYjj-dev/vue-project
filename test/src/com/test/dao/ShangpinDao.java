package com.test.dao;

import com.test.pojo.Shangpin;

import java.util.List;

public interface ShangpinDao {

    int addShangpin(Shangpin shangpin);
    int deleteShangpin(Integer id);
    int updateShangpin(Shangpin shangpin);
    Shangpin findShangpinById(int id);
    List<Shangpin> findShangpinByName(String name);
    List<Shangpin> findShangpinByGroup(String group);
    List<Shangpin> findAllShangpin();
}
