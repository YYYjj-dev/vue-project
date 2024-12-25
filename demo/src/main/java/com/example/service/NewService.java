package com.example.service;


import com.example.dao.NewDao;
import com.example.entity.New;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewService {

    @Resource
    private NewDao newDao;

    public List<New> getNew() {
        return newDao.getNew();
    }
}
