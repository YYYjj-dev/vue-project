package com.example.service;


import com.example.dao.RegulationDao;
import com.example.entity.Regulation;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegulationService {

    @Resource
    private RegulationDao regulationDao;

    public List<Regulation> getRegulations() {
        return regulationDao.getRegulations();
    }
}
