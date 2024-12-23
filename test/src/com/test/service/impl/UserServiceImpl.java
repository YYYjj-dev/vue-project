package com.test.service.impl;

import com.test.dao.impl.UserDaoImpl;
import com.test.pojo.User;
import com.test.service.UserService;
import com.test.dao.UserDao;


public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

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
