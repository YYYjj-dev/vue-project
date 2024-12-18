package com.test.dao;

import com.test.pojo.News;
import com.test.pojo.Regulation;

import java.util.List;

public class RegulationDao extends BaseDao{

    public List<Regulation> findAllAllRegular() {
        String sql = "select title,id from regulation";
        List<Regulation> itemList = baseQuery(Regulation.class, sql);
        return itemList;
    }

    public Regulation findRegulationById(Integer id) {
        String sql = "select title,content,date from regulation where id=?";
        List<Regulation> regulationList = baseQuery(Regulation.class, sql, id);
        return regulationList.isEmpty() ? null : regulationList.get(0);
    }
}
