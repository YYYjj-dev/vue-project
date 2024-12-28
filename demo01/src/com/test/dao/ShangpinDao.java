package com.test.dao;

import com.test.pojo.Score;
import com.test.pojo.Shangpin;

import java.util.List;

public interface ShangpinDao {

    int addShangpin(Shangpin shangpin);

    int updateShangpin(Shangpin shangpin);

    Shangpin findShangpinById(int id);

    List<Shangpin> findShangpinByName(String name);

    List<Shangpin> findShangpinByGroup(String group);

    List<Shangpin> findAllShangpin();

    int deleteShangpinById(Integer id);

    List<Shangpin> findShangpinByType(String type);

    List<Shangpin> findShangpinByMid(Integer mid);

    int addScore(Score score);

    String findScoreBySid(Integer sid);
}
