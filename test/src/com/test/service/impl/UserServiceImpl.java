package com.test.service.impl;

import com.test.dao.UserDao;
import com.test.pojo.User;
import com.test.service.UserService;
import com.test.util.MD5Util;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDao();

    @Override
    public int regist(User user) {
        user.setPassword(MD5Util.encrypt(user.getPassword()));
        int rows = userDao.addUser(user);
        return rows;
    }

    @Override
    public User findByUsername(String username) {
        User user =userDao.getUserByUsername(username);
        return user;
    }
}
