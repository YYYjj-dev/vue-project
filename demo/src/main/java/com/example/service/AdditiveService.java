package com.example.service;


import com.example.dao.AdditiveDao;
import com.example.entity.Additive;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdditiveService {

    @Resource
    private AdditiveDao additiveDao;

    public List<Additive> getAdditives() {
        return additiveDao.getAdditives();
    }
}
