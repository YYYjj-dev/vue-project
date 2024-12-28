package com.test.dao.impl;

import com.test.dao.BaseDao;
import com.test.dao.ImageDao;
import com.test.pojo.Image;


public class ImageDaoImpl extends BaseDao implements ImageDao {
    @Override
    public int addCarousel(Image image) {
        String sql = "insert into carousel values(DEFAULT,?,?)";
        return baseUpdate(sql,image.getPath(),image.getPart());
    }

    @Override
    public int deleteCarousel(Integer id) {
        String sql = "delete from carousel where id = ?";
        return baseUpdate(sql,id);
    }

}
