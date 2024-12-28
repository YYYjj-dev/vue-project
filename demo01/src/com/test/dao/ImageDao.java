package com.test.dao;

import com.test.pojo.Image;
import jakarta.servlet.http.HttpServletRequest;

public interface ImageDao {
    int addCarousel(Image image);

    int deleteCarousel(Integer id);
}
