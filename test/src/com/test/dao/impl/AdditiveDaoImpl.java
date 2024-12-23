package com.test.dao.impl;

import com.test.dao.AdditiveDao;
import com.test.dao.BaseDao;
import com.test.pojo.Additive;

import java.util.List;

public class AdditiveDaoImpl extends BaseDao implements AdditiveDao {

    @Override
    public List<Additive> findAllAdditive() {
        String sql = "select id,typename,name,description,usestandard_internal usestandardInternal,usestandard_international usestandardInternational,analysis,admin_id adminId,imgpath,url from additive";
        List<Additive> additives = baseQuery(Additive.class,sql);
        return additives;
    }
}
