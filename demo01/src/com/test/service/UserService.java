package com.test.service;

import com.test.pojo.Merchant;
import com.test.pojo.User;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    int addCollect(Integer uid, Integer sid);

    List<Merchant> findCollect(Integer uid);

    int normalRegist(User user);

    int merchantRegist(User user);

    int updateNormal(User user);

    int updateMerchant(User user);
}
