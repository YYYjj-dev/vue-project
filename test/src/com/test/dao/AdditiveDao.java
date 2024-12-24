package com.test.dao;

import com.test.pojo.Additive;

import java.util.List;

public interface AdditiveDao {
    public List<Additive> findAllAdditive();

    Additive findAdditiveById(Integer id);

    int addAdditive(Additive addAdditive);
}
