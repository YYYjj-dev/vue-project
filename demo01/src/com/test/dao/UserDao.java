package com.test.dao;

import com.test.pojo.Merchant;
import com.test.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    int addUser(User user);

    User getUserByUsername(String username);

    int addCollect(Integer uid, Integer sid);

    List<Merchant> findCollect(Integer uid);

    List<User> foundUserByType(String type);

    List<User> findAllUser();

    int updateUser(User user);

    int updatePassword(String newPassword);

    int deleteUser(String username);

    List<User> findUser(Map<String, Object> queryParams);

    User findUserById(Integer uid);
}
