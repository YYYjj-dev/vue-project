package com.test.dao.impl;
import com.test.dao.BaseDao;
import com.test.dao.UserDao;
import com.test.pojo.Merchant;
import com.test.pojo.Normaluser;
import com.test.pojo.User;

import java.util.List;


public class UserDaoImpl extends BaseDao implements UserDao {
    public int addUser(User user) {
        String type = user.getType();
        String sql = "insert into user values(DEFAULT,?,?,?)";
        return baseUpdate(sql,type,user.getUsername(),user.getPassword());
    }

    public User getUserByUsername(String username) {
        String sql = "select * from user where username = ?";
        List<User> user = baseQuery(User.class,sql,username);
        return user.isEmpty() ? null : user.get(0);
    }

    @Override
    public int addCollect(Integer uid, Integer sid) {
        String sql = "insert into collect values(?,?)";
        return baseUpdate(sql,uid,sid);
    }

    @Override
    public int findCollect(Integer uid) {
        String sql = "select * from collect where uid = ?";
        return baseUpdate(sql,uid);
    }

    public int Merchant(Merchant merchant) {
        String sql = "insert into merchant values(DEFAULT,?,?,?,?)";
        return 1;
     }

     public int addNormalUser(Normaluser normaluser) {
        String sql = "insert into normaluser values(DEFAULT,?,?,?,?)";
        return 1;
     }
}
