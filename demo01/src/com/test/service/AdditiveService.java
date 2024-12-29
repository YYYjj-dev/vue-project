package com.test.service;

import com.test.pojo.Additive;

import java.util.List;
import java.util.Map;

public interface AdditiveService {
    List<Additive> findAllAdditive();

    Additive findAdditiveById(Integer id);

    int addAdditive(Additive addAdditive);

    List<Additive> findAdditiveByName(String name);

    List<Additive> findAdditiveByType(String typeName);

    int updateAdditive(Additive additive);

    int deleteAdditive(Integer id);

    List<Additive> findAdditiveByNature(String nature);

    List<Additive> findAdditive(Map<String, Object> queryParams);

    ;

}
