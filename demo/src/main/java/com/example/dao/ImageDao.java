package com.example.dao;

import com.example.entity.Image;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ImageDao {

    @Select("select * from image where part = #{part}")
    List<Image> getImageByPart(String part);
}
