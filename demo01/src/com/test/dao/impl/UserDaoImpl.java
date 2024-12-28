package com.test.dao.impl;
import com.test.dao.BaseDao;
import com.test.dao.UserDao;
import com.test.pojo.Merchant;
import com.test.pojo.User;

import java.util.List;


public class UserDaoImpl extends BaseDao implements UserDao {

    public int addUser(User user) {
        String sql = "insert into user values(DEFAULT,?,?,?,?,?)";
        return baseUpdate(sql,user.getType(),user.getUsername(),user.getPassword(),user.getGender(),user.getImg());
    }

    public User getUserByUsername(String username) {
        String sql = "select * from user where username = ?";
        List<User> user = baseQuery(User.class,sql,username);
        return user.isEmpty() ? null : user.get(0);
    }

    @Override
    public int addCollect(Integer uid, Integer sid) {
        String sql = "insert into collect values(?,?)";
        return baseUpdate(sql, uid, sid);
    }

    @Override
    public List<Merchant> findCollect(Integer uid) {
        String sql = "select name,description,logo from collect,merchant where user_id = ? and merchant.id = store_id";
        return baseQuery(Merchant.class, sql, uid);
    }


    @Override
    public List<User> foundUserByType(String type) {
        String sql = "select * from user where type = ?";
        return baseQuery(User.class,sql,type);
    }

    @Override
    public List<User> findAllUser() {
        String sql = "select * from user";
        return baseQuery(User.class,sql);
    }

    @Override
    public int updateUser(User user) {
        String sql = "update user set gender = ?,img = ? where id = ?";
        return baseUpdate(sql,user.getGender(),user.getImg(),user.getId());
    }

    @Override
    public int updatePassword(String newPassword) {
        String sql = "update user set password = ? where id = ?";
        return baseUpdate(sql,newPassword);
    }

    @Override
    public int deleteUser(String username) {
        String sql = "delete from user where username = ?";
        return baseUpdate(sql,username);
    }
}