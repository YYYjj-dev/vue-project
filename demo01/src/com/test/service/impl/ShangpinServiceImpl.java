package com.test.service.impl;

import com.test.dao.ShangpinDao;
import com.test.dao.impl.ShangpinDaoImpl;
import com.test.pojo.Score;
import com.test.pojo.Shangpin;
import com.test.service.ShangpinService;

import java.util.List;
import java.util.Map;

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
    public int addShangpin(Shangpin shangpin) {
        return shangpinDao.addShangpin(shangpin);
    }

    @Override
    public List<Shangpin> findShangpinByGroup(String group) {
        return shangpinDao.findShangpinByGroup(group);
    }

    @Override
    public int updateShangpin(Shangpin shangpin) {
        return shangpinDao.updateShangpin(shangpin);
    }

    @Override
    public List<Shangpin> findShangpinByName(String name) {
        return shangpinDao.findShangpinByName(name);
    }

    @Override
    public List<Shangpin> findShangpinByType(String type) {
        return shangpinDao.findShangpinByType(type);
    }

    @Override
    public List<Shangpin> findShangpinByMid(Integer mid) {
        return shangpinDao.findShangpinByMid(mid);
    }

    @Override
    public int addScore(Score score) {
        return shangpinDao.addScore(score);
    }

    @Override
    public String findScoreBySid(Integer sid) {
        return shangpinDao.findScoreBySid(sid);
    }

    @Override
    public List<Shangpin> findShangpin(Map<String, Object> queryParams) {
        return shangpinDao.findShangpin(queryParams);
    }

    @Override
    public int deleteShangpinById(Integer id) {
        return shangpinDao.deleteShangpinById(id);
    }
}
