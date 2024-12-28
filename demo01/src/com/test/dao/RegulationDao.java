package com.test.dao;

import com.test.pojo.Regulation;

import java.util.List;

public interface RegulationDao {
    List<Regulation> findAllAllRegular();

    Regulation findRegulationById(Integer id);

    int addRegular(Regulation regulation);

    int updateRegular(Regulation regulation);

    int deleteRegular(Integer id);

    List<Regulation> findRegularByTitle(String title);
}
