package com.test.service.impl;

import com.test.dao.UserDao;
import com.test.pojo.User;
import com.test.service.UserService;


public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDao();

    @Override
    public int regist(User user) {
        int rows = userDao.addUser(user);
        return rows;
    }

    @Override
    public User findByUsername(String username) {
        User user =userDao.getUserByUsername(username);
        return user;
    }
}
