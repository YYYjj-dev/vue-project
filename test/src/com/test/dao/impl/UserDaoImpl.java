package com.test.dao.impl;
import com.test.dao.BaseDao;
import com.test.dao.UserDao;
import com.test.pojo.Merchant;
import com.test.pojo.Normaluser;
import com.test.pojo.User;

import java.text.Normalizer;
import java.util.List;


public class UserDaoImpl extends BaseDao implements UserDao {
    public int addUser(User user) {
        String type = user.getType();
        String sql = "insert into user values(DEFAULT,?,?,?)";
        if(type.equals("merchant")) {
            addMerchant((Merchant)user);
        }else if(type.equals("normaluser")) {
            addNormalUser((Normaluser)user);
        }
        return baseUpdate(sql,type,user.getUsername(),user.getPassword());
    }

    public User getUserByUsername(String username) {
        String sql = "select * from user where username = ?";
        List<User> user = baseQuery(User.class,sql,username);
        return user.isEmpty() ? null : user.get(0);
    }
     public int addMerchant(Merchant merchant) {
        String sql = "insert into merchant values(DEFAULT,?,?,?,?)";
        return 1;
     }
     public int addNormalUser(Normaluser normaluser) {
        String sql = "insert into normaluser values(DEFAULT,?,?,?,?)";
        return 1;
     }
}
