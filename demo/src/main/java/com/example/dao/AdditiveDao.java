package com.example.dao;


import com.example.entity.Additive;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdditiveDao {

    @Select("select * from additive")
    List<Additive> getAdditives();

}
