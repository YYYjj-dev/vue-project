package com.test.service.impl;

import com.test.dao.UserDao;
import com.test.dao.impl.UserDaoImpl;
import com.test.pojo.Merchant;
import com.test.pojo.User;
import com.test.service.UserService;

import java.util.List;


public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public int regist(User user) {
        return userDao.addUser(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public int addCollect(Integer uid, Integer sid) {
        return userDao.addCollect(uid,sid);
    }

    @Override
    public List<Merchant> findCollect(Integer uid) {
        return userDao.findCollect(uid);
    }

    @Override
    public List<User> foundUserByType(String type) {
        return userDao.foundUserByType(type);
    }

    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }


}
