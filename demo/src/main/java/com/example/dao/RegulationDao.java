package com.example.dao;


import com.example.entity.Regulation;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RegulationDao {

    @Select("select * from regulation")
    List<Regulation> getRegulations();

}
