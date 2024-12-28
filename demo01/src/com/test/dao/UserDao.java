package com.test.dao;

import com.test.pojo.Merchant;
import com.test.pojo.User;

import java.util.List;

public interface UserDao {

    User getUserByUsername(String username);

    int addCollect(Integer uid, Integer sid);

    List<Merchant> findCollect(Integer uid);

    int addNormal(User user);

    int addMerchant(User user);

    int updateNormal(User user);

    int updateMerchant(User user);
}
