package com.test.service;

import com.test.pojo.Shangpin;

import java.util.List;

public interface ShangpinService {
    int deleteShangpinById(Integer id);

    List<Shangpin> findAllShangpin();

    Shangpin findShangpinById(Integer id);

    int addShangpin(Shangpin shangpin);

    List<Shangpin> findShangpinByGroup(String group);

    int updateShangpin(Shangpin shangpin);

    List<Shangpin> findShangpinByName(String name);
}
