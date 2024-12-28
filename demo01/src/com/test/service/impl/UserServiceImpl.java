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
    public int normalRegist(User user) {
        return userDao.addNormal(user);
    }

    @Override
    public int merchantRegist(User user) {
        return userDao.addMerchant(user);
    }

    @Override
    public int updateNormal(User user) {
        return userDao.updateNormal(user);
    }

    @Override
    public int updateMerchant(User user) {
        return userDao.updateMerchant(user);
    }
}
