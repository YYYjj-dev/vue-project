package com.test.service.impl;

import com.test.dao.UserDao;
import com.test.dao.impl.UserDaoImpl;
import com.test.pojo.Merchant;
import com.test.pojo.User;
import com.test.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;


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

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public int updatePassword(String newPassword) {
        return userDao.updatePassword(newPassword);
    }

    @Override
    public int deleteUser(String username) {
        return userDao.deleteUser(username);
    }

    @Override
    public List<User> findUser(Map<String, Object> queryParams) {
        return userDao.findUser(queryParams);
    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public User findUserById(Integer uid) {
        return userDao.findUserById(uid);
    }

}
