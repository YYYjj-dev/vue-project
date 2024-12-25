package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.RegulationDao;
import com.test.pojo.Regulation;

import java.util.List;

public class RegulationDaoImpl extends BaseDao implements RegulationDao {

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

    @Override
    public int addRegular(Regulation regulation) {
        String sql ="insert into regulation(title,content,date,admin_id) values(?,?,?,?)";
        int rows = baseUpdate(sql,regulation.getTitle(),regulation.getContent(),regulation.getContent(),regulation.getAdminId());
        return  rows;
    }

    @Override
    public int updateRegular(Regulation regulation) {
        String sql ="update regulation set title=?,content=?,date=? where id=?";
        int rows = baseUpdate(sql,regulation.getTitle(),regulation.getContent(),regulation.getContent(),regulation.getId());
        return rows;
    }

    @Override
    public int deleteRegular(Integer id) {
        String sql = "delete from regulation where id=?";
        int rows = baseUpdate(sql,id);
        return rows;
    }
}
