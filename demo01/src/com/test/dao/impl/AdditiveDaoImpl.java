package com.test.dao.impl;

import com.test.dao.AdditiveDao;
import com.test.dao.BaseDao;
import com.test.pojo.Additive;

import java.util.List;
import java.util.Map;

public class AdditiveDaoImpl extends BaseDao implements AdditiveDao {

    @Override
    public List<Additive> findAllAdditive() {
        String sql = "select id,typename,name,description, usestandardInternal, usestandardInternational,analysis,imgpath,nature from additive";
        List<Additive> additivesList = baseQuery(Additive.class, sql);
        return additivesList;
    }

    @Override
    public Additive findAdditiveById(Integer id) {
        String sql = "select id,typename,name,description, usestandardInternal, usestandardInternational,analysis,imgpath,nature from additive where id = ?";
        List<Additive> additives = baseQuery(Additive.class, sql, id);
        return additives.isEmpty() ? null : additives.get(0);
    }

    public List<Additive> findAdditiveByName(String name) {
        String sql = "select id,typename,name,description,usestandardInternal,usestandardInternational,analysis,imgpath,nature from additive where name like ?";
        Object[] args = {"%" + name + "%"};
        List<Additive> additivesList = baseQuery(Additive.class, sql, args);
        return additivesList;
    }

    @Override
    public List<Additive> findAdditiveByNature(String nature) {
        String sql = "select id,typename,name,description,usestandardInternal,usestandardInternational,analysis,imgpath,nature from additive where nature = ?";
        List<Additive> additives = baseQuery(Additive.class, sql, nature);
        return additives;
    }

    @Override
    public List<Additive> findAdditive(Map<String, Object> queryParams) {
        String sql = buildQuery(queryParams,"*","additive");
        List<Additive> additives = baseQuery(Additive.class, sql);
        return additives;
    }

    @Override
    public Integer getAdditiveNum() {
        String sql = "select count(*) from additive";
        return baseQueryObject(Integer.class, sql);
    }

    @Override
    public int addAdditive(Additive addAdditive) {
        String sql = "insert into additive values(DEFAULT,?,?,?,?,?,?,?,?)";
        if(addAdditive.getImgpath() == null||addAdditive.getImgpath().equals("")){
            addAdditive.setImgpath("additiveDefault.png");
        }
        return baseUpdate(sql, addAdditive.getTypename(),
                addAdditive.getName(), addAdditive.getDescription(),
                addAdditive.getUsestandardInternal(), addAdditive.getUsestandardInternational(),
                addAdditive.getAnalysis(),
                addAdditive.getImgpath(), addAdditive.getNature());
    }

    @Override
    public List<Additive> findAdditiveByType(String typeName) {
        String sql = "select id,typename,name,description,usestandardInternal, usestandardInternational,analysis,imgpath,nature from additive where typename = ?";
        List<Additive> additives = baseQuery(Additive.class, sql, typeName);
        return additives;
    }

    public int deleteAdditive(Integer id) {
        String sql = "delete from additive where id =?";
        return baseUpdate(sql, id);
    }

    public int updateAdditive(Additive additive) {

        String sql = "update additive set typename =?, name =?, description =?, usestandardInternal =?, usestandardInternational =?, analysis =?, imgpath =?,nature=? where id =?";
        return baseUpdate(sql, additive.getTypename(), additive.getName(),
                additive.getDescription(), additive.getUsestandardInternal(),
                additive.getUsestandardInternational(), additive.getAnalysis(),
                 additive.getImgpath(),additive.getNature(),
                 additive.getId());
    }

}
