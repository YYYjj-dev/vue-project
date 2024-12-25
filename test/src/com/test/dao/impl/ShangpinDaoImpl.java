package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.ShangpinDao;
import com.test.pojo.Cases;
import com.test.pojo.Shangpin;
import com.test.util.OrderUtils;


import java.util.List;

public class ShangpinDaoImpl extends BaseDao implements ShangpinDao {
    @Override
    public int addShangpin(Shangpin shangpin) {
        String sql = "insert into shangpin (id,store_id,group,type,name,description,standard,price,num) values(DEFAULT,?,?,?,?,?,?,?,?)";
        return baseUpdate(sql, shangpin.getStoreId(), shangpin.getGruop(), shangpin.getName(), shangpin.getDescription(),shangpin.getStandard(),shangpin.getPrice(),shangpin.getNum());
    }

    @Override
    public int deleteShangpinById(Integer id) {
        String sql = "delete from shangpin where id = ?";
        return baseUpdate(sql, id);
    }

    @Override
    public int updateShangpin(Shangpin shangpin) {
        String sql = """
                UPDATE shangpin
                SET group = ?, type = ?,name = ?, description = ?, standard = ?, price =?, num =?
                WHERE id=?;""";
        return baseUpdate(sql, shangpin.getGruop(), shangpin.getType(), shangpin.getName(), shangpin.getDescription(), shangpin.getStandard(), shangpin.getPrice(), shangpin.getNum(), shangpin.getId());
    }

    @Override
    public Shangpin findShangpinById(int id) {
        String sql = "select id,store_id storeId,gruop,type,`name`,description,standard,price,score,num,img from shangpin where id = ?";
        List<Shangpin> shangpin = baseQuery(Shangpin.class, sql, id);
         if (shangpin.size() > 0) {
            return shangpin.get(0);
        } else {
            return null;}
    }

    @Override
    public List<Shangpin> findShangpinByName(String name) {
        String sql = "select * from cases where title like ?";
        Object[] args = {"%" + name + "%"};
        return baseQuery(Shangpin.class, sql, args);
    }

    @Override
    public List<Shangpin> findShangpinByGroup(String group) {
        String sql = "select * from shangpin where group = ?";
        List<Shangpin> shangpinList = baseQuery(Shangpin.class, sql,group);
        return shangpinList;
    }

    @Override
    public List<Shangpin> findAllShangpin() {
        String sql = "select id,store_id storeId,gruop,type,`name`,description,standard,price,score,num,img from shangpin\n";
        List<Shangpin> shangpinList = baseQuery(Shangpin.class, sql);
        return shangpinList;
    }


}
