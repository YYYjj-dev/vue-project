package com.test.dao;

import com.test.pojo.Regulation;

import java.util.List;

public interface RegulationDao {
    List<Regulation> findAllAllRegular();

    Regulation findRegulationById(Integer id);
}
