package com.example.dao;

import com.example.entity.New;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NewDao {

    @Select(" select * from news")
    List<New> getNew();
}
