package com.test.service;

import com.test.pojo.Merchant;
import com.test.pojo.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface UserService {

    int regist(User user);

    User findByUsername(String username);

    int addCollect(Integer uid, Integer sid);

    List<Merchant> findCollect(Integer uid);

    List<User> foundUserByType(String type);

    List<User> findAllUser();

    int updateUser(User user);

    int updatePassword(String newPassword);

    int deleteUser(String username);
}
