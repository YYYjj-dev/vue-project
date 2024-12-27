package com.test.service;

import com.test.pojo.User;

public interface UserService {

    int regist(User user);

    User findByUsername(String username);

    int addCollect(Integer uid, Integer sid);

    int findCollect(Integer uid);
}
