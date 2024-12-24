package com.test.service.impl;

import com.test.dao.ShangpinDao;
import com.test.dao.impl.ShangpinDaoImpl;
import com.test.pojo.Shangpin;
import com.test.service.ShangpinService;
import java.util.List;

public class ShangpinServiceImpl implements ShangpinService {
    private ShangpinDao shangpinDao=new ShangpinDaoImpl();



    @Override
    public List<Shangpin> findAllShangpin() {
        return shangpinDao.findAllShangpin();
    }

    @Override
    public Shangpin findShangpinById(Integer id) {
        return shangpinDao.findShangpinById(id);
    }

    @Override
    public int deleteShangpinById(Integer id) {
        return shangpinDao.deleteShangpinById(id);
    }
}
