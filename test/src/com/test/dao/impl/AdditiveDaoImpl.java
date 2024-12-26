package com.test.dao.impl;

import com.test.dao.AdditiveDao;
import com.test.dao.BaseDao;
import com.test.pojo.Additive;

import java.util.List;

public class AdditiveDaoImpl extends BaseDao implements AdditiveDao {

    @Override
    public List<Additive> findAllAdditive() {
        String sql = "select id,typename,name,description, usestandardInternal, usestandardInternational,analysis,admin_id adminId,imgpath from additive";
        List<Additive> additivesList = baseQuery(Additive.class, sql);
        return additivesList;
    }

    @Override
    public Additive findAdditiveById(Integer id) {
        String sql = "select id,typename,name,description,usestandard_internal usestandardInternal,usestandard_international usestandardInternational,analysis,admin_id adminId,imgpath,url from additive where id = ?";
        List<Additive> additives = baseQuery(Additive.class, sql, id);
        return additives.isEmpty() ? null : additives.get(0);
    }

    public List<Additive> findAdditiveByName(String name) {
        String sql = "select id,typename,name,description,usestandard_internal usestandardInternal,usestandard_international usestandardInternational,analysis,admin_id adminId,imgpath,url from additive where name like ?";
        Object[] args = {"%" + name + "%"};
        List<Additive> additivesList = baseQuery(Additive.class, sql, args);
        return additivesList;
    }

    @Override
    public int addAdditive(Additive addAdditive) {
        String sql = "insert into additive values(DEFAULT,?,?,?,?,?,?,?,?,?)";
        return baseUpdate(sql, addAdditive.getTypename(),
                addAdditive.getName(), addAdditive.getDescription(),
                addAdditive.getUsestandardInternal(), addAdditive.getUsestandardInternational(),
                addAdditive.getAnalysis(), addAdditive.getAdminId(),
                addAdditive.getImgpath(), addAdditive.getUrl());
    }

    @Override
    public List<Additive> findAdditiveByType(String typeName) {
        String sql = "\"select id,typename,name,description,usestandard_internal usestandardInternal,usestandard_international usestandardInternational,analysis,admin_id adminId,imgpath,url from additive where typename = ?";
        List<Additive> additives = baseQuery(Additive.class, sql, typeName);
        return additives;
    }

    public int deleteAdditive(Integer id) {
        String sql = "delete from additive where id =?";
        return baseUpdate(sql, id);
    }

    public int updateAdditive(Additive additive) {
        String sql = "update additive set typename =?, name =?, description =?, usestandard_internal =?, usestandard_international =?, analysis =?, admin_id =?, imgpath =?, url =? where id =?";
        return baseUpdate(sql, additive.getTypename(), additive.getName(),
                additive.getDescription(), additive.getUsestandardInternal(),
                additive.getUsestandardInternational(), additive.getAnalysis(),
                additive.getAdminId(), additive.getImgpath(),
                additive.getUrl(), additive.getId());
    }

}
