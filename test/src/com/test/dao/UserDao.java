package com.test.dao;

import com.test.pojo.User;

public interface UserDao {
    int addUser(User user);

    User getUserByUsername(String username);
}
