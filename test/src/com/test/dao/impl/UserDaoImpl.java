package com.test.dao.impl;
import com.test.dao.BaseDao;
import com.test.dao.UserDao;
import com.test.pojo.User;

import java.util.List;


public class UserDaoImpl extends BaseDao implements UserDao {
    public int addUser(User user) {
        String sql = "insert into user values(DEFAULT,3,?,?)";
        return baseUpdate(sql,user.getUsername(),user.getPassword());
    }

    public User getUserByUsername(String username) {
        String sql = "select * from user where username = ?";
        List<User> user = baseQuery(User.class,sql,username);
        return user.isEmpty() ? null : user.get(0);
    }

}
