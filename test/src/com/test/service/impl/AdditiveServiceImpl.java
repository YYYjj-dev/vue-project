package com.test.service.impl;

import com.test.dao.AdditiveDao;
import com.test.dao.impl.AdditiveDaoImpl;
import com.test.pojo.Additive;
import com.test.service.AdditiveService;

import java.util.List;

public class AdditiveServiceImpl implements AdditiveService {
    private AdditiveDao additiveDao = new AdditiveDaoImpl();

    @Override
    public List<Additive> findAllAdditive() {
        return additiveDao.findAllAdditive();
    }

    @Override
    public Additive findAdditiveById(Integer id) {
        return additiveDao.findAdditiveById(id);
    }

    @Override
    public int addAdditive(Additive addAdditive) {
        return additiveDao.addAdditive(addAdditive);
    }
}
