package com.test.dao;

import com.test.pojo.Merchant;
import com.test.pojo.User;

import java.util.List;

public interface UserDao {

    int addUser(User user);

    User getUserByUsername(String username);

    int addCollect(Integer uid, Integer sid);

    List<Merchant> findCollect(Integer uid);

    List<User> foundUserByType(String type);

    List<User> findAllUser();

}
