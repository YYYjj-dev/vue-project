package com.test.service.impl;

import com.test.dao.UserDao;
import com.test.dao.impl.UserDaoImpl;
import com.test.pojo.User;
import com.test.service.UserService;


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

    @Override
    public int addCollect(Integer uid, Integer sid) {
        return userDao.addCollect(uid,sid);
    }

    @Override
    public int findCollect(Integer uid) {
        return userDao.findCollect(uid);
    }
}
