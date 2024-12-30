package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.NumDao;

import java.util.HashMap;
import java.util.Map;

public class NumDaoImpl extends BaseDao implements NumDao {
    @Override
    public Map<String,Integer> getAllNum() {
        String sql = "select count(*) from additive";
        Map<String,Integer> map = new HashMap<>();
        map.put("additive",getNum(sql));
        sql="select count(*) from regulation";
        map.put("regular",getNum(sql));
        sql = "select count(*) from news";
        map.put("news",getNum(sql));
        sql = "select count(*) from cases";
        map.put("case",getNum(sql));
        sql = "select count(*) from merchant";
        map.put("merchant",getNum(sql));
        sql = "select count(*) from user";
        map.put("user",getNum(sql));
        sql = "select count(*) from order";
        map.put("order",getNum(sql));
        sql = "select count(*) from shangpin";
        map.put("shangpin",getNum(sql));
        sql = "select count(*) from feedback";
        map.put("feedback",getNum(sql));
        return map;
    }

    private Integer getNum(String sql) {
        return baseQueryObject(Integer.class,sql);
    }
}
