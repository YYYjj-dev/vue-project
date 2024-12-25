package com.example.service;

import com.example.dao.UserDao;
import com.example.entity.User;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Resource
    private UserDao userDao;

    public List<User> getUser() {
        return userDao.getUser();
    }
}
